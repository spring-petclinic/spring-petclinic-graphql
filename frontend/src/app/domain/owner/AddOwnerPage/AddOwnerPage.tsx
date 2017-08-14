import * as React from "react";
import { gql, graphql, QueryProps, DefaultChildProps, MutationFunc } from "react-apollo";
import { withRouter, RouteComponentProps } from "react-router-dom";
import * as AddOwnerMutationGql from "./AddOwnerMutation.graphql";
import { AddOwnerMutation, AddOwnerInput, AddOwnerMutationVariables, OwnerData } from "../../types";

import OwnerEditForm from "../OwnerEditForm";

const emptyOwner = (): OwnerData => ({
  firstName: "",
  lastName: "",
  address: "",
  city: "",
  telephone: ""
});

type AddOwnerPageOwnProps = RouteComponentProps<{}>;
type AddOwnerPageProps = AddOwnerPageOwnProps & {
  mutate: MutationFunc<AddOwnerMutation>;
};

const AddOwnerPage = ({ mutate, history }: AddOwnerPageProps) =>
  <OwnerEditForm
    initialOwner={emptyOwner()}
    formTitle="Add Owner"
    onFormSubmit={(owner: OwnerData) => {
      return mutate({
        variables: { input: owner }
      })
        .then(({ data }) => {
          history.push(`/owners/${data.addOwner.owner.id}`);
        })
        .catch(error => {
          console.log("there was an error sending the query", error);
          return Promise.reject(`Could not save owner: ${error}`);
        });
    }}
  />;

export default withRouter(graphql<AddOwnerMutation, AddOwnerPageOwnProps>(AddOwnerMutationGql)(AddOwnerPage));
