import * as React from "react";
import { OwnerFragment } from "../../types";

import { Link } from "react-router-dom";

export default ({ owner }: { owner: OwnerFragment }) =>
  <section>
    <h2>Owner Information</h2>
    <table className="table table-striped">
      <tbody>
        <tr>
          <th>Name</th>
          <td>
            <b>
              {owner.firstName} {owner.lastName}
            </b>
          </td>
        </tr>
        <tr>
          <th>Address</th>
          <td>
            {owner.address}
          </td>
        </tr>
        <tr>
          <th>City</th>
          <td>
            {owner.city}
          </td>
        </tr>
        <tr>
          <th>Telephone</th>
          <td>
            {owner.telephone}
          </td>
        </tr>
      </tbody>
    </table>
    <Link to={`/owners/${owner.id}/edit`} className="btn btn-default">
      Edit Owner
    </Link>
    &nbsp;
    <Link to={`/owners/${owner.id}/pets/new`} className="btn btn-default">
      Add New Pet
    </Link>
  </section>;
