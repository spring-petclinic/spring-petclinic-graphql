import * as React from "react";
import NewVisitForm from "./NewVisitForm";
import Button from "@/components/Button";

type NewVisitPanelProps = { petId: number; petName: string };

export default function NewVisitPanel({ petId, petName }: NewVisitPanelProps) {
  const [formOpen, setFormOpen] = React.useState(false);

  if (!formOpen) {
    return (
      <Button
        onClick={() => setFormOpen(true)}
        aria-label={`Add visit for pet ${petName}`}
      >
        New Visit
      </Button>
    );
  }

  return (
    <NewVisitForm
      onFinish={() => setFormOpen(false)}
      petId={petId}
      petName={petName}
    />
  );
}
