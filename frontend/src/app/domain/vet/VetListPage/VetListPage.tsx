import * as React from "react";
import { RouteComponentProps } from "react-router";
import { gql, graphql, QueryProps, DefaultChildProps } from "react-apollo";

import * as VetListQueryGql from "./VetListQuery.graphql";
import { VetsQuery, OwnerSummaryFragment } from "../../types";
import withLoadingHandler from "../../../components/withLoadingHandler";

type VetListPageOwnProps = RouteComponentProps<{}>;
type VetListPageProps = VetListPageOwnProps & {
  data: QueryProps & VetsQuery;
};

const VetListPage = ({ data: { vets } }: VetListPageProps) =>
  <section>
    <h2>Veterinarians</h2>
    <table className="table table-striped">
      <thead>
        <tr>
          <th>Name</th>
          <th>Specialties</th>
        </tr>
      </thead>
      <tbody>
        {vets.map(vet =>
          <tr key={vet.id}>
            <td>
              {vet.firstName} {vet.lastName}
            </td>
            <td>
              {vet.specialties.length > 0 ? vet.specialties.map(specialty => specialty.name).join(", ") : "none"}
            </td>
          </tr>
        )}
      </tbody>
    </table>
  </section>;

export default graphql<VetsQuery, VetListPageOwnProps>(VetListQueryGql)(withLoadingHandler(VetListPage));
