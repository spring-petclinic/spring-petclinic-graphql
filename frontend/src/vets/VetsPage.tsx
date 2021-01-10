import Heading from "components/Heading";
import Link from "components/Link";
import PageLayout from "components/PageLayout";
import Section from "components/Section";
import Table from "components/Table";
import {
  AllVetsQuery,
  useAllVetsQuery,
  useVetAndVisitsQuery,
} from "generated/graphql-types";
import * as React from "react";
import { useParams } from "react-router-dom";

type Vet = AllVetsQuery["vets"][number];

export default function VetsPage() {
  const { vetId } = useParams<{ vetId?: string }>();
  const { loading, data, error } = useAllVetsQuery();

  if (loading || error || !data) {
    return (
      <PageLayout title="Veterinarians">
        <Heading>{error ? "Loading failed" : "Loading"}</Heading>
      </PageLayout>
    );
  }

  function vetRow(vet: Vet) {
    const fullname = `${vet.firstName} ${vet.lastName}`;
    const specialties = vet.specialties.map((s) => s.name).join(", ");
    if (String(vet.id) === vetId) {
      return [<b>{fullname}</b>, <b>{specialties}</b>];
    }

    return [<Link to={`/vets/${vet.id}`}>{fullname}</Link>, specialties];
  }

  return (
    <PageLayout title="Veterinarians">
      <Table labels={["Name", "Specialities"]} values={data.vets.map(vetRow)} />
      {vetId && <VisitsForVet vetId={parseInt(vetId)} />}
    </PageLayout>
  );
}

type VisitsForVetProps = {
  vetId: number;
};

function VisitsForVet({ vetId }: VisitsForVetProps) {
  const { loading, error, data } = useVetAndVisitsQuery({
    variables: {
      vetId,
    },
  });

  if (loading) {
    return <>Loading...</>;
  }

  if (error) {
    return <>Error...</>;
  }

  if (!data || !data.vet) {
    return <>Not found</>;
  }

  return (
    <Section>
      <Heading>
        Treatments of {data.vet.firstName} {data.vet.lastName}
      </Heading>
      <Table
        labels={["Date", "Pet", "Owner"]}
        values={data.vet.visits.visits.map((visit) => [
          visit.date,
          visit.pet.name,
          <Link to={`/owners/${visit.pet.owner.id}`}>
            {visit.pet.owner.firstName} {visit.pet.owner.lastName}
          </Link>,
        ])}
      />
    </Section>
  );
}
