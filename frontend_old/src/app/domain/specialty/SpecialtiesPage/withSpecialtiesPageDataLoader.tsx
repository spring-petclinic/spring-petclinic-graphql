import * as React from "react";
import { RouteComponentProps } from "react-router";
import { graphql, QueryProps } from "react-apollo";

import * as LoadSpecialtiesQueryGql from "./LoadSpecialtiesQuery.graphql";
import { ReactFunctionOrComponentClass, LoadSpecialtiesQuery } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";

type withSpecialtiesPageDataLoaderProps = RouteComponentProps<{
  petId: string;
}>;

type TargetComponentProps = {
  data: QueryProps & LoadSpecialtiesQuery;
};

const withSpecialtiesPageDataLoader = (TargetComponent: ReactFunctionOrComponentClass<TargetComponentProps>) => {
  return graphql<LoadSpecialtiesQuery, withSpecialtiesPageDataLoaderProps, TargetComponentProps>(LoadSpecialtiesQueryGql)(
    withLoadingHandler(TargetComponent)
  );
};

export default withSpecialtiesPageDataLoader;
