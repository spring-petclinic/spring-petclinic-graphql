import {
  useAddVisitMutation,
  useAllVetNamesQuery,
} from "@/generated/graphql-types.ts";
import dayjs from "dayjs";
import { useForm } from "react-hook-form";
import Heading from "@/components/Heading.tsx";
import Input from "@/components/Input.tsx";
import Label from "@/components/Label.tsx";
import Select from "@/components/Select.tsx";
import ButtonBar from "@/components/ButtonBar.tsx";
import Button from "@/components/Button.tsx";
import { Section } from "@/components/Section.tsx";
import { filterNull } from "@/utils.ts";

type VisitFormData = {
  description: string;
  date: Date;
  vet?: string;
};

const emptyVetOption = {
  value: -1,
  label: "",
};

type NewVisitFormProps = {
  onFinish(): void;
  petId: number;
  petName: string;
};

export default function NewVisitForm({
  onFinish,
  petId,
  petName,
}: NewVisitFormProps) {
  const {
    loading: vetsLoading,
    data: vetsData,
    error: vetsError,
  } = useAllVetNamesQuery();
  const [addVisit, { called, loading, error }] = useAddVisitMutation();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<VisitFormData>();

  const vetOptions = vetsData
    ? [
        emptyVetOption,
        ...vetsData.vets.edges
          .filter(filterNull)
          .map((v) => v.node)
          .map((vet) => ({
            value: vet.id,
            label: `${vet.firstName} ${vet.lastName}`,
          })),
      ]
    : null;

  async function handleAddClick({ description, date, vet }: VisitFormData) {
    const petclinicDate = dayjs(date).format("YYYY/MM/DD");
    const vetId = vet ? parseInt(vet) : emptyVetOption.value;
    const result = await addVisit({
      variables: {
        input: {
          petId,
          description,
          date: petclinicDate,
          vetId: vetId === emptyVetOption.value ? null : vetId,
        },
      },
    });
    if (result.data) {
      onFinish();
    }
  }

  return (
    <Section aria-label={`Add visit for pet ${petName}`}>
      <Heading level="3">Add Visit</Heading>

      <Input
        type="date"
        {...register("date", { required: true, valueAsDate: true })}
        label="Date"
        error={errors.date && "Please enter a valid date"}
      />
      <Input
        type="text"
        {...register("description", { required: true })}
        label="Description"
        error={errors.description && "Please fill in a description"}
      />
      {vetsError && (
        <Label>
          Could not load Vets. You can create a new Visit but cannot assign it
          to a Vet yet.
        </Label>
      )}
      {vetsLoading && <Label type="info">Loading Vets...</Label>}
      {vetOptions && (
        <Select
          {...register("vet")}
          label="Vet (optional)"
          options={vetOptions}
          defaultValue={emptyVetOption.value}
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
      {error && <Label>Saving failed</Label>}
    </Section>
  );
}
