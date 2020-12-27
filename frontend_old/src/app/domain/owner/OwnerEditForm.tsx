import * as React from "react";

import { MutationFunc } from "react-apollo";
import EditForm from "../../components/form/EditForm";
import { InputFactory } from "../../components/form/FormElements";
import { Digits, NotEmpty } from "../../components/form/Constraints";
import { OwnerData } from "../types";

const copyOwner = (owner: OwnerData): OwnerData => ({
  address: owner.address,
  city: owner.city,
  firstName: owner.firstName,
  lastName: owner.lastName,
  telephone: owner.telephone
});

const ownerFormElements = [
  {
    name: "firstName",
    label: "First Name",
    constraint: NotEmpty,
    elementComponentFactory: InputFactory
  },
  {
    elementComponentFactory: InputFactory,
    name: "lastName",
    label: "Last Name",
    constraint: NotEmpty
  },
  {
    elementComponentFactory: InputFactory,
    name: "address",
    label: "Address",
    constraint: NotEmpty
  },
  {
    elementComponentFactory: InputFactory,
    name: "city",
    label: "City",
    constraint: NotEmpty
  },
  {
    elementComponentFactory: InputFactory,
    name: "telephone",
    label: "Telephone",
    constraint: Digits(10)
  }
];

type OwnerEditFormProps = {
  initialOwner: OwnerData;
  formTitle: string;
  onFormSubmit: (owner: OwnerData) => void | Promise<string | void>;
};

const OwnerEditForm = <M, MI>({ formTitle, initialOwner, onFormSubmit }: OwnerEditFormProps) => {
  return (
    <EditForm
      formTitle={formTitle}
      copyModel={copyOwner}
      formElements={ownerFormElements}
      initialModel={initialOwner}
      onFormSubmit={onFormSubmit}
    />
  );
};

export default OwnerEditForm;
