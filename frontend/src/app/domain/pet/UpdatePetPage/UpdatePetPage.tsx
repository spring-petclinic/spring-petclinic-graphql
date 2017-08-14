import * as React from "react";
import { gql, graphql, QueryProps, DefaultChildProps, MutationFunc } from "react-apollo";
import { withRouter, RouteComponentProps } from "react-router-dom";
import * as UpdatePetMutationGql from "./UpdatePetMutation.graphql";
import { GetUpdatePetFormDataQuery, UpdatePetInput, UpdatePetMutation, PetData } from "../../types";
import PetEditForm from "../PetForm";

import withUpdatePetFormData from "./withUpdatePetFormData";

type UpdatePetPageOwnProps = RouteComponentProps<{}> & {
  formData: GetUpdatePetFormDataQuery;
};
type UpdatePetPageProps = UpdatePetPageOwnProps & {
  mutate: MutationFunc<UpdatePetMutation>;
};

const UpdatePetPage = ({ mutate, history, formData }: UpdatePetPageProps) =>
  <div>
    <PetEditForm
      formTitle={`Update Pet ${formData.pet.name} from ${formData.pet.owner.firstName} ${formData.pet.owner.lastName}`}
      initialPet={{
        ...formData.pet,
        type: formData.pet.type.id
      }}
      pettypes={formData.pettypes}
      onFormSubmit={(pet: PetData) => {
        const input: UpdatePetInput = {
          ...pet,
          petId: formData.pet.id,
          typeId: pet.type,
          ownerId: formData.pet.owner.id
        };
        return mutate({
          variables: { input }
        })
          .then(({ data }) => {
            history.push(`/owners/${formData.pet.owner.id}`);
          })
          .catch(error => {
            console.log("there was an error sending the query", error);
            return Promise.reject(`Could not save owner: ${error}`);
          });
      }}
    />
  </div>;

export default withUpdatePetFormData(graphql<UpdatePetMutation, UpdatePetPageOwnProps>(UpdatePetMutationGql)(UpdatePetPage));
