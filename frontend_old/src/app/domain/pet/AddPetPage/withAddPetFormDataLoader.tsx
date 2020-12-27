import * as React from "react";
import { RouteComponentProps } from "react-router";
import { graphql, QueryProps } from "react-apollo";

import * as GetAddPetFormDataQueryGql from "./GetAddPetFormDataQuery.graphql";
import { ReactFunctionOrComponentClass, GetAddPetFormDataQuery } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";

type withAddPetFormDataLoaderProps = RouteComponentProps<{
  ownerId: string;
}>;

type TargetComponentProps = {
  data: QueryProps & GetAddPetFormDataQuery;
};

const withAddPetFormDataLoader = (TargetComponent: ReactFunctionOrComponentClass<TargetComponentProps>) => {
  return graphql<GetAddPetFormDataQuery, withAddPetFormDataLoaderProps, TargetComponentProps>(GetAddPetFormDataQueryGql, {
    options: ({ match }) => ({
      variables: {
        ownerId: match.params.ownerId
      }
    })
  })(withLoadingHandler(TargetComponent));
};

export default withAddPetFormDataLoader;
