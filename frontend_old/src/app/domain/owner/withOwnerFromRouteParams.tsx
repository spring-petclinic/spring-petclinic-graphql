import * as React from "react";
import { RouteComponentProps } from "react-router";
import { gql, graphql, QueryProps, DefaultChildProps } from "react-apollo";

import * as OwnerQueryGql from "./OwnerQuery.graphql";
import { ReactFunctionOrComponentClass, OwnerQuery, OwnerDetailsFragment } from "../types";
import withLoadingHandler from "../../components/withLoadingHandler";

// Specifies the parameters taken from the route definition (/.../:ownerId)
type OwnerPageRouteParams = {
  ownerId: number;
};

// Specifies the Properties that are passed to
type OwnerPageProps = RouteComponentProps<OwnerPageRouteParams>;

// The "full set" of properties passed to the target component
// (that is with the properties from GraphQL including the loaded owner)
type OwnerPageFullProps = OwnerPageProps & {
  data: QueryProps & OwnerQuery;
  owner: OwnerDetailsFragment;
};

// this function takes a Component, that must have OwnerPageProps-compatible properties.
// The function loads the Owner with the ownerId specified in the route params
// and passes the loaded owner to the specified Component
const withOwnerFromRouteParams = (
  TheOwnerComponent: ReactFunctionOrComponentClass<{
    owner: OwnerDetailsFragment;
  }>
) => {
  const withOwnerFromRouteParamsWrapper = (props: OwnerPageFullProps) => <TheOwnerComponent owner={props.data.owner} />;
  return graphql<OwnerQuery, OwnerPageProps>(OwnerQueryGql, {
    options: ({ match }) => ({
      variables: {
        ownerId: match.params.ownerId
      }
    })
  })(withLoadingHandler(withOwnerFromRouteParamsWrapper));
};

export default withOwnerFromRouteParams;
