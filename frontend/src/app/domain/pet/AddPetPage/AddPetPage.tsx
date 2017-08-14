import * as React from "react";
import { graphql, QueryProps, MutationFunc } from "react-apollo";
import { withRouter, RouteComponentProps } from "react-router-dom";
import * as AddPetMutationGql from "./AddPetMutation.graphql";
import * as OwnerQueryGql from "../../owner/OwnerQuery.graphql";
import { GetAddPetFormDataQuery, AddPetInput, AddPetMutation, PetData } from "../../types";
import PetForm from "../PetForm";

import withAddPetFormDataLoader from "./withAddPetFormDataLoader";

type AddPetPageOwnProps = RouteComponentProps<{}> & {
  data: QueryProps & GetAddPetFormDataQuery;
};
type AddPetPageProps = AddPetPageOwnProps & {
  mutate: MutationFunc<AddPetMutation>;
};

const emptyPet = (): PetData => ({
  name: "",
  birthDate: "",
  type: -1
});

const AddPetPage = ({ mutate, history, data }: AddPetPageProps) =>
  <div>
    <PetForm
      formTitle={`Add Pet for ${data.owner.firstName} ${data.owner.lastName}`}
      initialPet={emptyPet()}
      pettypes={data.pettypes}
      onFormSubmit={(pet: PetData) => {
        const input: AddPetInput = {
          ...pet,
          typeId: pet.type,
          ownerId: data.owner.id
        };
        return mutate({
          variables: { input },
          refetchQueries: [
            {
              query: OwnerQueryGql,
              variables: {
                ownerId: data.owner.id
              }
            }
          ]
        })
          .then(() => {
            history.push(`/owners/${data.owner.id}`);
          })
          .catch(error => {
            console.log("there was an error sending the query", error);
            return Promise.reject(`Could not save owner: ${error}`);
          });
      }}
    />
  </div>;

export default withAddPetFormDataLoader(graphql<AddPetMutation, AddPetPageOwnProps>(AddPetMutationGql)(AddPetPage));
