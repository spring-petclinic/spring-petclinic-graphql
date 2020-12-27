import * as React from "react";
import { RouteComponentProps } from "react-router";
import { gql, graphql, QueryProps, DefaultChildProps } from "react-apollo";

import * as getUpdatePetFormDataQueryGql from "./GetUpdatePetFormDataQuery.graphql";
import { ReactFunctionOrComponentClass, GetUpdatePetFormDataQuery } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";

type PetFormDataProps = RouteComponentProps<{
  ownerId: string;
  petId: string;
}>;

type WrapperComponentProps = PetFormDataProps & {
  data: QueryProps & GetUpdatePetFormDataQuery;
};

const withAddPetFormData = (
  TargetComponent: ReactFunctionOrComponentClass<{
    formData: GetUpdatePetFormDataQuery;
  }>
) => {
  const WrapperComponent = (props: WrapperComponentProps) => <TargetComponent formData={props.data} {...props} />;

  return graphql<GetUpdatePetFormDataQuery, PetFormDataProps>(getUpdatePetFormDataQueryGql, {
    options: ({ match }) => ({
      variables: {
        petId: match.params.petId
      }
    })
  })(withLoadingHandler(WrapperComponent));
};

export default withAddPetFormData;
