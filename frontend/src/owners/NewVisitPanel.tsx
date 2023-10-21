import * as React from "react";
import NewVisitForm from "./NewVisitForm";
import Button from "@/components/Button";

type NewVisitPanelProps = { petId: number };

export default function NewVisitPanel({ petId }: NewVisitPanelProps) {
  const [formOpen, setFormOpen] = React.useState(false);

  if (!formOpen) {
    return <Button onClick={() => setFormOpen(true)}>New Visit</Button>;
  }

  return <NewVisitForm onFinish={() => setFormOpen(false)} petId={petId} />;
}
