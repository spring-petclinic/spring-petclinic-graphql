import * as React from "react";
import { gql, graphql, QueryProps, DefaultChildProps, MutationFunc } from "react-apollo";
import { withRouter, RouteComponentProps } from "react-router-dom";
import * as UpdateOwnerMutationGql from "./UpdateOwnerMutation.graphql";
import { UpdateOwnerMutation, UpdateOwnerInput, UpdateOwnerMutationVariables, OwnerDetailsFragment } from "../../types";

import OwnerEditForm from "../OwnerEditForm";
import withOwnerFromRouteParams from "../withOwnerFromRouteParams";

type UpdateOwnerPageRouteParams = {
  ownerId: number;
};

type UpdateOwnerPageProps = RouteComponentProps<UpdateOwnerPageRouteParams> & {
  owner: OwnerDetailsFragment;
};

type UpdateOwnerFullPageProps = UpdateOwnerPageProps & {
  mutate: MutationFunc<UpdateOwnerMutation>;
};

// The 'actual' component that receives all the props
const UpdateOwnerPage = ({
  owner, // from withOwnerFromRouteParams HOC
  history, // from withRouter HOC
  match,
  mutate // from graphql HOC
}: UpdateOwnerFullPageProps) =>
  <OwnerEditForm
    initialOwner={owner}
    formTitle="Update Owner"
    onFormSubmit={owner => {
      return mutate({
        variables: {
          input: {
            ownerId: match.params.ownerId,
            ...owner
          }
        }
      })
        .then(({ data }) => {
          history.push(`/owners/${data.updateOwner.owner.id}`);
        })
        .catch(error => {
          console.log("there was an error sending the update mutation", error);
          return Promise.reject(`Could not save owner: ${error}`);
        });
    }}
  />;

export default // load the owner that is specified in the URL (.../:ownerId) via Graphql and add it to props
// (until the owner is fetched from the GraphQL backend a 'Loading...' component is shown)
withOwnerFromRouteParams(
  // add 'history' object from the Router to the props
  withRouter(
    // add GraphQL 'mutate' function (for running the UpdateOwnerMutation) to props
    graphql<UpdateOwnerMutation, UpdateOwnerPageProps>(UpdateOwnerMutationGql)(
      // ... and finally the 'actual' components that receives all the props
      UpdateOwnerPage
    )
  )
);
