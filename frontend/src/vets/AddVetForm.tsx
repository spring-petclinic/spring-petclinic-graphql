import Button from "components/Button";
import ButtonBar from "components/ButtonBar";
import Heading from "components/Heading";
import Input from "components/Input";
import Label from "components/Label";
import Section from "components/Section";
import Select from "components/Select";
import {
  useAddVetMutation,
  useAllSpecialtiesQuery,
} from "generated/graphql-types";
import * as React from "react";
import { useForm } from "react-hook-form";

type AddVetFormProps = {
  onFinish(): void;
};

type VetFormData = {
  firstName: string;
  lastName: string;
  specialtyIds: number[];
};

export default function AddVetForm({ onFinish }: AddVetFormProps) {
  const {
    data: specialtiesData,
    error: specialtiesError,
    loading: specialtiesLoading,
  } = useAllSpecialtiesQuery();
  const [addVet, { called, loading, data }] = useAddVetMutation();
  const { register, errors, handleSubmit } = useForm<VetFormData>();

  const specialtiesOptions = specialtiesData
    ? [
        ...specialtiesData.specialties.map((s) => ({
          label: s.name,
          value: s.id,
        })),
        { label: "invalid (use to see errors in response)", value: 666 },
      ]
    : null;

  async function handleAddClick({
    firstName,
    lastName,
    specialtyIds,
  }: VetFormData) {
    console.log("DATA", firstName, lastName, specialtyIds);

    const result = await addVet({
      variables: {
        input: {
          firstName,
          lastName,
          specialtyIds: specialtyIds || [],
        },
      },
    });
    if (result.data && "vet" in result.data.result) {
      onFinish();
    }
  }

  let errorMsg =
    data && "error" in data.result
      ? `Saving failed: ${data.result.error}`
      : null;

  return (
    <Section>
      <Heading level="3">Add Veterinary</Heading>

      <Input
        type="text"
        name="firstName"
        ref={register({ required: true })}
        label="First name"
        error={errors.firstName && "Please enter a valid first name"}
      />
      <Input
        type="text"
        name="lastName"
        ref={register({ required: true })}
        label="Last name"
        error={errors.firstName && "Please enter a valid last name"}
      />

      {specialtiesError && (
        <Label>
          Could not load Vets. You can create a new Vet but cannot assign it to
          Specialities yet.
        </Label>
      )}
      {specialtiesLoading && (
        <Label type="info">Specialities are loading</Label>
      )}
      {specialtiesOptions && (
        <Select
          label="Specialties"
          name="specialtyIds"
          multiple
          ref={register}
          options={specialtiesOptions}
        />
      )}

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
      {errorMsg && <Label>{errorMsg}</Label>}
    </Section>
  );
}
