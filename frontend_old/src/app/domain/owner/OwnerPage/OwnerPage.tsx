import * as React from "react";
import { RouteComponentProps } from "react-router";
import { gql, graphql, QueryProps, DefaultChildProps } from "react-apollo";

import * as OwnerQueryGql from "./OwnerQuery.graphql";
import { OwnerDetailsFragment } from "../../types";
import withOwnerFromRouteParams from "../withOwnerFromRouteParams";

import PetsTable from "./PetsTable";
import OwnerDetailsTable from "./OwnerDetailsTable";

const OwnerPage = ({ owner }: { owner: OwnerDetailsFragment }) =>
  <span>
    <OwnerDetailsTable owner={owner} />
    <PetsTable owner={owner} />
  </span>;

export default withOwnerFromRouteParams(OwnerPage);
