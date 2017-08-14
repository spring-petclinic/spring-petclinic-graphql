import * as React from "react";

import EditForm from "../../components/form/EditForm";
import { InputFactory, DateInputFactory, SelectInputFactory } from "../../components/form/FormElements";
import { Digits, NotEmpty } from "../../components/form/Constraints";
import { SelectFormElement } from "../../components/form/types";
import { PetData, PetType } from "../types";

type PetFormProps = {
  initialPet: PetData;
  pettypes: PetType[];
  formTitle: string;
  onFormSubmit: (pet: PetData) => void | Promise<string | void>;
};

const PetForm = ({ formTitle, initialPet, pettypes, onFormSubmit }: PetFormProps) => {
  return (
    <EditForm
      formTitle={formTitle}
      initialModel={initialPet}
      onFormSubmit={onFormSubmit}
      formElements={[
        {
          name: "name",
          label: "Name",
          constraint: NotEmpty,
          elementComponentFactory: InputFactory
        },
        {
          elementComponentFactory: DateInputFactory,
          name: "birthDate",
          label: "Birthdate",
          constraint: NotEmpty
        },
        {
          elementComponentFactory: SelectInputFactory,
          name: "type",
          label: "Types",
          options: pettypes,
          constraint: NotEmpty
        } as SelectFormElement
      ]}
    />
  );
};

export default PetForm;
