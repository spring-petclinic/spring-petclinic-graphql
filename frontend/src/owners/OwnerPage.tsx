import Button from "components/Button";
import Heading from "components/Heading";
import Link from "components/Link";
import PageLayout from "components/PageLayout";
import Section from "components/Section";
import Table from "components/Table";
import { useFindOwnerWithPetsAndVisitsQuery } from "generated/graphql-types";
import * as React from "react";
import { useParams } from "react-router-dom";
import NewVisitPanel from "./NewVisitPanel";

export default function OwnerPage() {
  const { ownerId } = useParams<{ ownerId: string }>();

  const { loading, data, error } = useFindOwnerWithPetsAndVisitsQuery({
    variables: {
      ownerId: parseInt(ownerId),
    },
  });

  if (loading) {
    return <PageLayout title="Owners">Loading</PageLayout>;
  } else if (error || !data) {
    return <PageLayout title="Owners">Error</PageLayout>;
  }

  return (
    <PageLayout
      title={`Owners - ${data.owner.firstName} ${data.owner.lastName}`}
    >
      <Section>
        <Table
          values={[
            ["Name", `${data.owner.firstName} ${data.owner.lastName}`],
            ["Address", data.owner.address],
            ["City", data.owner.city],
            ["Telephone", data.owner.telephone],
          ]}
        ></Table>
      </Section>
      <div className="mb-4">
        <Heading level="2">Pets and Visits</Heading>
      </div>
      {data.owner.pets.map((pet) => (
        <div key={pet.id} className="mb-8">
          <Section invert>
            <div className="md:flex items-baseline justify-between border-b-4 border-spr-white mb-2 pb-2">
              <Heading level="3">
                {pet.name} ({pet.type.name}, * {pet.birthDate})
              </Heading>
              <Button type="link">Edit {pet.name}</Button>
            </div>
            {pet.visits.visits.length ? (
              <Table
                labels={["Visit Date", "Treating vet", "Description"]}
                values={pet.visits.visits.map((visit) => [
                  visit.date,
                  visit.treatingVet ? (
                    <Link to={`/vets/${visit.treatingVet.id}`}>
                      {visit.treatingVet.firstName} {visit.treatingVet.lastName}
                    </Link>
                  ) : (
                    ""
                  ),
                  visit.description,
                ])}
              />
            ) : (
              <b className="block mb-4">No visits yet</b>
            )}
            <NewVisitPanel petId={pet.id} />
          </Section>
        </div>
      ))}
    </PageLayout>
  );
}
