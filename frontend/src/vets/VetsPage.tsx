import * as React from "react";
import { useParams } from "react-router-dom";
import AddVetForm from "./AddVetForm";
import VetsOverview from "./VetsOverview";
import PageLayout from "@/components/PageLayout";

export default function VetsPage() {
  const { vetId } = useParams<{ vetId?: string }>();
  const [formOpen, setFormOpen] = React.useState<boolean>(false);

  return (
    <PageLayout title="Manage Veterinarians">
      {formOpen || (
        <VetsOverview vetId={vetId} onAddVetClick={() => setFormOpen(true)} />
      )}
      {formOpen && <AddVetForm onFinish={() => setFormOpen(false)} />}
    </PageLayout>
  );
}
