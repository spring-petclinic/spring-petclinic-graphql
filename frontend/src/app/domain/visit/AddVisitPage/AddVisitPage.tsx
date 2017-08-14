import * as React from "react";
import { RouteComponentProps } from "react-router-dom";
import { graphql, QueryProps, MutationFunc } from "react-apollo";
import * as AddVisitMutationGql from "./AddVisitMutation.graphql";
import * as OwnerQueryGql from "../../owner/OwnerQuery.graphql";

import { LoadAddVisitPageDataQuery, AddVisitMutation, VisitData, AddVisitInput } from "../../types";
import withAddVisitPageDataLoader from "./withAddVisitPageDataLoader";
import PetInfoTable from "./PetInfoTable";
import VisitForm from "./VisitForm";

type AddVisitPageOwnProps = RouteComponentProps<{
  ownerId: string;
  petId: string;
}> & {
  data: QueryProps & LoadAddVisitPageDataQuery;
};

type AddPetPageProps = AddVisitPageOwnProps & {
  mutate: MutationFunc<AddVisitMutation>;
};

const AddVisitPage = ({ data, mutate, history, match }: AddPetPageProps) =>
  <div>
    <PetInfoTable data={data} />
    <VisitForm
      initialVisit={{
        description: "",
        date: ""
      }}
      onFormSubmit={(visit: VisitData) => {
        const input: AddVisitInput = {
          ...visit,
          petId: data.pet.id
        };
        return mutate({
          variables: { input },
          refetchQueries: [
            {
              query: OwnerQueryGql,
              variables: {
                ownerId: match.params.ownerId
              }
            }
          ]
        })
          .then(() => {
            history.push(`/owners/${match.params.ownerId}`);
          })
          .catch(error => {
            console.log("there was an error sending the query", error);
            return Promise.reject(`Could not save owner: ${error}`);
          });
      }}
    />
  </div>;

export default withAddVisitPageDataLoader(graphql<AddVisitMutation, AddVisitPageOwnProps>(AddVisitMutationGql)(AddVisitPage));
