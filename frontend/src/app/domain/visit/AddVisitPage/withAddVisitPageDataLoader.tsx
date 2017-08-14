import * as React from "react";
import { RouteComponentProps } from "react-router";
import { graphql, QueryProps } from "react-apollo";

import * as LoadAddVisitPageDataQueryGql from "./LoadAddVisitPageDataQuery.graphql";
import { ReactFunctionOrComponentClass, LoadAddVisitPageDataQuery, LoadAddVisitPageDataQueryVariables } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";

type withAddVisitPageDataLoaderProps = RouteComponentProps<{
  petId: string;
}>;

type TargetComponentProps = {
  data: QueryProps & LoadAddVisitPageDataQuery;
};

const withAddVisitPageDataLoader = (TargetComponent: ReactFunctionOrComponentClass<TargetComponentProps>) => {
  return graphql<LoadAddVisitPageDataQuery, withAddVisitPageDataLoaderProps, TargetComponentProps>(LoadAddVisitPageDataQueryGql, {
    options: ({ match }) => ({
      variables: {
        petId: match.params.petId
      }
    })
  })(withLoadingHandler(TargetComponent));
};

export default withAddVisitPageDataLoader;
