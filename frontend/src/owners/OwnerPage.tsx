import { useParams } from "react-router-dom";
import NewVisitPanel from "./NewVisitPanel";
import Button from "@/components/Button";
import Heading from "@/components/Heading";
import PageLayout from "@/components/PageLayout";
import Table from "@/components/Table";
import {
  OnNewVisitDocument,
  OnNewVisitSubscription,
  useFindOwnerWithPetsAndVisitsQuery,
} from "@/generated/graphql-types";
import { invariant } from "@apollo/client/utilities/globals";
import Link from "@/components/Link.tsx";
import { useEffect } from "react";
import { Section, SectionHeading } from "@/components/Section.tsx";
import { produce } from "immer";

export default function OwnerPage() {
  const { ownerId } = useParams<{ ownerId: string }>();
  invariant(ownerId, "Missing param ownerId");

  const { loading, data, error, subscribeToMore } =
    useFindOwnerWithPetsAndVisitsQuery({
      variables: {
        ownerId: parseInt(ownerId),
      },
    });

  useEffect(() => {
    subscribeToMore<OnNewVisitSubscription>({
      document: OnNewVisitDocument,
      updateQuery: (prev, { subscriptionData }) => {
        const newVisit = subscriptionData.data.newVisit;
        if (!newVisit) {
          return prev;
        }

        console.log("Received newVisit", newVisit);

        if (newVisit.pet.owner.id !== parseInt(ownerId)) {
          console.log(
            "new visit received for owner, ignoring: ",
            newVisit.pet.owner.id,
          );
          return prev;
        }

        const petId = newVisit.pet.id;

        const newOwner = produce(prev, (draft) => {
          const pet = draft.owner.pets.find((p) => p.id === petId);
          if (!pet) {
            return draft;
          }

          if (pet.visits.visits.find((v) => v.id === newVisit.id)) {
            console.log("visit alread add to pet");
            return draft;
          }

          console.log("Add newVisit to pet", pet);

          pet.visits.visits.push(newVisit);
          return draft;
        });

        return newOwner;
      },
    });
  }, [ownerId, subscribeToMore]);
  //
  // // const x = useOnNewVisitSubscription();
  // // console.log(x.loading, x.data);

  if (loading) {
    return <PageLayout title="Owners">Loading</PageLayout>;
  } else if (error || !data) {
    return <PageLayout title="Owners">Error</PageLayout>;
  }

  return (
    <PageLayout
      title={`Owners - ${data.owner.firstName} ${data.owner.lastName}`}
    >
      <Section
        aria-label={`Contact data ${data.owner.firstName} ${data.owner.lastName}`}
      >
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
          <Section invert aria-label={`Visits of ${pet.name}`}>
            <SectionHeading>
              <Heading level="3">
                {pet.name} ({pet.type.name}, * {pet.birthDate})
              </Heading>
              <Button type="link">Edit {pet.name}</Button>
            </SectionHeading>
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
            <NewVisitPanel petId={pet.id} petName={pet.name} />
          </Section>
        </div>
      ))}
    </PageLayout>
  );
}
