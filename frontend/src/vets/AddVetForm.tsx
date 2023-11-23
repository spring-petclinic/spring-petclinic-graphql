import Button from "@/components/Button";
import ButtonBar from "@/components/ButtonBar";
import Heading from "@/components/Heading";
import Input from "@/components/Input";
import Label from "@/components/Label";
import Select from "@/components/Select";
import {
  useAddVetMutation,
  useAllSpecialtiesQuery,
} from "@/generated/graphql-types.ts";
import { useForm } from "react-hook-form";
import { Section } from "@/components/Section.tsx";

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
  const [addVet, { called, loading, data, error }] = useAddVetMutation({
    // Errors in Responses should be retured in 'error' (and not as rejected promise)
    // https://www.apollographql.com/docs/react/api/react/hoc/#optionserrorpolicy
    errorPolicy: "all",
  });
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<VetFormData>();

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

  const errorMsg = error
    ? error.message
    : data && "error" in data.result
    ? `Saving failed: ${data.result.error}`
    : null;

  return (
    <Section>
      <Heading level="3">Add Veterinary</Heading>

      <Input
        type="text"
        {...register("firstName", { required: true })}
        label="First name"
        error={errors.firstName && "Please enter a valid first name"}
      />
      <Input
        type="text"
        {...register("lastName", { required: true })}
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
          multiple
          {...register("specialtyIds")}
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
