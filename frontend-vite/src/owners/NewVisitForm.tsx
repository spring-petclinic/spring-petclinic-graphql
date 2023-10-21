import { gql } from "@apollo/client";
import {
  PetVisitsFragment,
  useAddVisitMutation,
  useAllVetNamesQuery,
} from "@/generated/graphql-types.ts";
import { produce } from "immer";
import dayjs from "dayjs";
import { useForm } from "react-hook-form";
import Section from "@/components/Section.tsx";
import Heading from "@/components/Heading.tsx";
import Input from "@/components/Input.tsx";
import Label from "@/components/Label.tsx";
import Select from "@/components/Select.tsx";
import ButtonBar from "@/components/ButtonBar.tsx";
import Button from "@/components/Button.tsx";

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
  vet?: string;
};

const emptyVetOption = {
  value: -1,
  label: "",
};

type NewVisitFormProps = {
  onFinish(): void;
  petId: number;
};

export default function NewVisitForm({ onFinish, petId }: NewVisitFormProps) {
  const {
    loading: vetsLoading,
    data: vetsData,
    error: vetsError,
  } = useAllVetNamesQuery();
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
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<VisitFormData>();

  const vetOptions = vetsData
    ? [
        emptyVetOption,
        ...vetsData.vets.map((vet) => ({
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
    <Section>
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
