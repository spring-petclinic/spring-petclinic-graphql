import * as React from "react";
import { LoadAddVisitPageDataQuery } from "../../types";

const PetInfoTable = ({ data: { pet } }: { data: LoadAddVisitPageDataQuery }) =>
  <table className="table table-striped">
    <thead>
      <tr>
        <th>Name</th>
        <th>Birth Date</th>
        <th>Type</th>
        <th>Owner</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>
          {pet.name}
        </td>
        <td>
          {pet.birthDate}
        </td>
        <td>
          {pet.type.name}
        </td>
        <td>
          {pet.owner.firstName} {pet.owner.lastName}
        </td>
      </tr>
    </tbody>
  </table>;

export default PetInfoTable;
