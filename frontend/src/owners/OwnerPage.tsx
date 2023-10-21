import { useParams } from "react-router-dom";
import NewVisitPanel from "./NewVisitPanel";
import Button from "@/components/Button";
import Heading from "@/components/Heading";
import PageLayout from "@/components/PageLayout";
import Section from "@/components/Section";
import Table from "@/components/Table";
import { useFindOwnerWithPetsAndVisitsQuery } from "@/generated/graphql-types";
import { invariant } from "@apollo/client/utilities/globals";
import Link from "@/components/Link.tsx";

export default function OwnerPage() {
  const { ownerId } = useParams<{ ownerId: string }>();
  invariant(ownerId, "Missing param ownerId");

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
            <div className="mb-2 items-baseline justify-between border-b-4 border-spr-white pb-2 md:flex">
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
              <b className="mb-4 block">No visits yet</b>
            )}
            <NewVisitPanel petId={pet.id} />
          </Section>
        </div>
      ))}
    </PageLayout>
  );
}
