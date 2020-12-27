import * as React from "react";

import LinkButton from "../../../components/LinkButton";

import { graphql, compose, QueryProps, MutationFunc, ApolloExecutionResult } from "react-apollo";
import * as LoadSpecialtiesQueryGql from "./LoadSpecialtiesQuery.graphql";
import * as AddSpecialtyMutationGql from "./AddSpecialtyMutation.graphql";
import * as VetListQueryGql from "../../vet/VetListPage/VetListQuery.graphql";
import * as UpdateSpecialtyMutationGql from "./UpdateSpecialtyMutation.graphql";
import * as RemoveSpecialtyMutationGql from "./RemoveSpecialtyMutation.graphql";

import { LoadSpecialtiesQuery, AddSpecialtyMutation, UpdateSpecialtyMutation, RemoveSpecialtyMutation } from "../../types";
import withSpecialtiesPageDataLoader from "./withSpecialtiesPageDataLoader";
import SpecialtyEditor, { Message } from "./SpecialtyEditor";

type SpecialtiesPageOwnProps = {
  data: QueryProps & LoadSpecialtiesQuery;
};

type SpecialtiesPageProps = SpecialtiesPageOwnProps & {
  addSpecialty: MutationFunc<AddSpecialtyMutation>;
  updateSpecialty: MutationFunc<UpdateSpecialtyMutation>;
  removeSpecialty: MutationFunc<RemoveSpecialtyMutation>;
};

type Specialty = {
  id: number;
  name: string;
};

type SpecialtiesPageState = {
  messages: { [key: string]: Message | null };
};

const PSEUDO_NEW_ID = "___NEW___";

class SpecialtiesPage extends React.Component<SpecialtiesPageProps, SpecialtiesPageState> {
  constructor(props: SpecialtiesPageProps) {
    super(props);

    this.state = { messages: {} };
  }

  clearMessage(id: string | number) {
    this.setMessage(id, null);
  }

  setErrorMessage(id: string | number, message: string) {
    this.setMessage(id, {
      kind: "ERROR",
      message
    });
  }

  setOkMessage(id: string | number, message: string) {
    this.setMessage(id, {
      kind: "OK",
      message
    });
  }

  setMessage(id: string | number, msg: Message | null) {
    const { messages } = this.state;
    this.setState({
      messages: {
        ...messages,
        [id]: msg
      }
    });
  }

  render() {
    const { data: { specialties, refetch }, updateSpecialty, addSpecialty, removeSpecialty } = this.props;
    const { messages } = this.state;
    return (
      <section>
        <h2>Edit or add specialties</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {specialties.map(s =>
              <SpecialtyEditor
                key={s.id}
                initialSpecialty={s.name}
                message={messages[s.id]}
                onUpdateSpecialty={name => {
                  this.clearMessage(s.id);
                  return updateSpecialty({
                    variables: {
                      input: {
                        specialtyId: s.id,
                        name
                      }
                    }
                  })
                    .then(({ data }) => {
                      this.setOkMessage(s.id, "Saved");
                    })
                    .catch(error => {
                      console.log("there was an error sending the update mutation", error);
                      this.setErrorMessage(s.id, `Could not update specialty: ${error}`);
                    });
                }}
                onRemoveSpecialty={() => {
                  this.clearMessage(s.id);
                  return removeSpecialty({
                    variables: {
                      input: {
                        specialtyId: s.id
                      }
                    },
                    refetchQueries: [
                      {
                        query: VetListQueryGql
                      }
                    ]
                  })
                    .then(({ data }) => {
                      this.setOkMessage(s.id, "Saved");
                      refetch();
                    })
                    .catch(error => {
                      console.log("there was an error sending the update mutation", error);
                      this.setErrorMessage(s.id, `Could not update specialty: ${error}`);
                    });
                }}
                onChange={() => this.clearMessage(s.id)}
                onBlur={() => this.clearMessage(s.id)}
              />
            )}

            <SpecialtyEditor
              initialSpecialty={""}
              message={messages[PSEUDO_NEW_ID]}
              onUpdateSpecialty={name => {
                this.clearMessage(PSEUDO_NEW_ID);
                return addSpecialty({
                  variables: {
                    input: {
                      name
                    }
                  },

                  update: (proxy, { data }: ApolloExecutionResult<AddSpecialtyMutation>) => {
                    if (data) {
                      const existingSpecialties = proxy.readQuery({ query: LoadSpecialtiesQueryGql });
                      (existingSpecialties as any).specialties.push(data.addSpecialty.specialty);
                      proxy.writeQuery({
                        query: LoadSpecialtiesQueryGql,
                        data: existingSpecialties
                      });
                    }
                  }
                })
                  .then(({ data }) => {
                    this.setOkMessage(PSEUDO_NEW_ID, "Saved");
                  })
                  .catch(error => {
                    console.log("there was an error sending the add mutation", error);
                    this.setErrorMessage(PSEUDO_NEW_ID, `Could not add specialty: ${error}`);
                  });
              }}
              onChange={() => this.clearMessage(PSEUDO_NEW_ID)}
              onBlur={() => this.clearMessage(PSEUDO_NEW_ID)}
            />
          </tbody>
        </table>
        <LinkButton to="/">Home</LinkButton>
      </section>
    );
  }
}

export default withSpecialtiesPageDataLoader(
  compose(
    graphql<AddSpecialtyMutation, SpecialtiesPageOwnProps>(AddSpecialtyMutationGql, {
      name: "addSpecialty"
    }),
    graphql<UpdateSpecialtyMutation, SpecialtiesPageOwnProps>(UpdateSpecialtyMutationGql, {
      name: "updateSpecialty"
    }),
    graphql<RemoveSpecialtyMutation, SpecialtiesPageOwnProps>(RemoveSpecialtyMutationGql, {
      name: "removeSpecialty"
    })
  )(SpecialtiesPage)
);
