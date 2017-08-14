import * as React from "react";
import { Link, RouteComponentProps } from "react-router-dom";
import { gql, graphql, QueryProps, DefaultChildProps } from "react-apollo";

import * as OwnerListQueryGql from "./OwnerListQuery.graphql";
import { OwnerListQuery, OwnerSummaryFragment } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";
import LinkButton from "../../../components/LinkButton";

const OwnerRow = ({ owner }: { owner: OwnerSummaryFragment }) =>
  <tr key={owner.id}>
    <td>
      <Link to={`/owners/${owner.id}`}>
        {owner.firstName} {owner.lastName}
      </Link>
    </td>
    <td className="hidden-sm hidden-xs">
      {owner.address}
    </td>
    <td>
      {owner.city}
    </td>
    <td>
      {owner.telephone}
    </td>
    <td className="hidden-xs">
      {owner.pets.map(pet => pet.name).join(", ")}
    </td>
    <td className="hidden-xs">
      {owner.pets.reduce((total, currentPet) => total + currentPet.visits.totalCount, 0) || ""}
    </td>
  </tr>;

const OwnersTable = ({ owners }: { owners: OwnerSummaryFragment[] }) =>
  <table className="table table-striped">
    <thead>
      <tr>
        <th>Name</th>
        <th className="hidden-sm hidden-xs">Address</th>
        <th>City</th>
        <th>Telephone</th>
        <th className="hidden-xs">Pets</th>
        <th className="hidden-xs">Visits</th>
      </tr>
    </thead>
    <tbody>
      {owners.map(owner => <OwnerRow key={owner.id} owner={owner} />)}
    </tbody>
  </table>;

type OwnerListPageOwnProps = RouteComponentProps<{}>;
type OwnerListPageProps = {
  data: QueryProps & OwnerListQuery;
};

const OwnerListPage = ({ data: { owners } }: OwnerListPageProps) =>
  <section>
    <h2>
      {owners.length} Owners found
    </h2>
    <OwnersTable owners={owners} />
    <LinkButton to="/owners/add">Add Owner</LinkButton>
  </section>;

export default graphql<OwnerListQuery, OwnerListPageOwnProps, OwnerListPageProps>(OwnerListQueryGql)(
  withLoadingHandler(OwnerListPage)
);
