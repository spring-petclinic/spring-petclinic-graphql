import { gql } from "@apollo/client";
import Button from "components/Button";
import ButtonBar from "components/ButtonBar";
import Heading from "components/Heading";
import Input from "components/Input";
import Label from "components/Label";
import Section from "components/Section";
import dayjs from "dayjs";
import {
  useAddVisitMutation,
  PetVisitsFragment,
} from "generated/graphql-types";
import produce from "immer";
import * as React from "react";
import { useForm } from "react-hook-form";

/** Fragment for updating the Cache after mutation (adding new Visit to existing Pet) */
const PetVisits = gql`
  fragment PetVisits on Pet {
    id
    visitConnection: visits {
      visits {
        id
      }
    }
  }
`;

type VisitFormData = {
  description: string;
  date: Date;
};

type NewVisitFormProps = {
  onFinish(): void;
  petId: number;
};

export default function NewVisitForm({ onFinish, petId }: NewVisitFormProps) {
  const [addVisit, { called, loading, error }] = useAddVisitMutation({
    update(cache, { data }) {
      if (!data) {
        return;
      }
      const existingPet = cache.readFragment<PetVisitsFragment>({
        fragment: PetVisits,
        id: `Pet:${petId}`,
      });
      if (existingPet) {
        const newData = produce(existingPet, (draftPet) => {
          draftPet.visitConnection.visits.push(data.addVisit.visit);
        });
        cache.writeFragment<PetVisitsFragment>({
          fragment: PetVisits,
          data: newData,
        });
      }
    },
  });
  const { register, errors, handleSubmit } = useForm();

  async function handleAddClick({ description, date }: VisitFormData) {
    const petclinicDate = dayjs(date).format("YYYY/MM/DD");
    const result = await addVisit({
      variables: {
        input: {
          petId,
          description,
          date: petclinicDate,
        },
      },
    });
    if (result.data) {
      onFinish();
    }
  }

  return (
    <Section>
      <Heading level="3">Add Visit</Heading>

      <Input
        type="date"
        name="date"
        ref={register({ required: true, valueAsDate: true })}
        label="Date"
        error={errors.date && "Please enter a valid date"}
      />
      <Input
        type="text"
        name="description"
        ref={register({ required: true })}
        label="Description"
        error={errors.description && "Please fill in a description"}
      />
      <ButtonBar align="left">
        {called && loading ? (
          <Button disabled>Saving...</Button>
        ) : (
          <Button onClick={handleSubmit(handleAddClick)}>Save</Button>
        )}
        <Button type="secondary" onClick={onFinish}>
          Cancel
        </Button>
      </ButtonBar>
      {error && <Label>Saving failed</Label>}
    </Section>
  );
}
