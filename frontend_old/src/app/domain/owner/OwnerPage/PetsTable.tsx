import * as React from "react";

import { Link } from "react-router-dom";
import LinkButton from "../../../components/LinkButton";
import { OwnerDetailsFragment, PetDetailsFragment } from "../../types";

const VisitsTable = ({ ownerId, pet }: { ownerId: number; pet: PetDetailsFragment }) =>
  <table className="table-condensed">
    <thead>
      <tr>
        <th>Visit Date</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
      {pet.visits.visits.map(visit =>
        <tr key={visit.id}>
          <td>
            {visit.date}
          </td>
          <td>
            {visit.description}
          </td>
        </tr>
      )}
      <tr>
        <td>
          <LinkButton to={`/owners/${ownerId}/pets/${pet.id}/visits/new`}>Add Visit</LinkButton>
        </td>
      </tr>
    </tbody>
  </table>;

export default ({ owner }: { owner: OwnerDetailsFragment }) =>
  <section>
    <h2>Pets and Visits</h2>
    {owner.pets.length === 0
      ? <h3>This owner has no Pets</h3>
      : <table className="table table-striped">
          <tbody>
            {owner.pets.map(pet =>
              <tr key={pet.id}>
                <td style={{ verticalAlign: "top" }}>
                  <dl className="dl-horizontal">
                    <dt>Name</dt>
                    <dd>
                      {pet.name}
                    </dd>
                    <dt>Birth Date</dt>
                    <dd>
                      {pet.birthDate}
                    </dd>
                    <dt>Type</dt>
                    <dd>
                      {pet.type.name}
                    </dd>
                    <dt>
                      <LinkButton to={`/owners/${owner.id}/pets/${pet.id}/edit`}>Edit Pet</LinkButton>
                    </dt>
                  </dl>
                </td>
                <td style={{ verticalAlign: "top" }}>
                  <VisitsTable ownerId={owner.id} pet={pet} />
                </td>
              </tr>
            )}
          </tbody>
        </table>}
  </section>;
