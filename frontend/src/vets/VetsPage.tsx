import PageLayout from "components/PageLayout";
import { AllVetsQuery } from "generated/graphql-types";
import * as React from "react";
import { useParams } from "react-router-dom";
import AddVetForm from "./AddVetForm";
import VetsOverview from "./VetsOverview";

export default function VetsPage() {
  const { vetId } = useParams<{ vetId?: string }>();
  const [formOpen, setFormOpen] = React.useState<boolean>(false);

  return (
    <PageLayout title="Veterinarians">
      {formOpen || (
        <VetsOverview vetId={vetId} onAddVetClick={() => setFormOpen(true)} />
      )}
      {formOpen && <AddVetForm onFinish={() => setFormOpen(false)} />}
    </PageLayout>
  );
}
