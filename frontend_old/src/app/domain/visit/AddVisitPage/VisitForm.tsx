import * as React from "react";

import EditForm from "../../../components/form/EditForm";
import { InputFactory, DateInputFactory, SelectInputFactory } from "../../../components/form/FormElements";
import { Digits, NotEmpty } from "../../../components/form/Constraints";
import { SelectFormElement } from "../../../components/form/types";
import { VisitData } from "../../types";

type VisitFormProps = {
  initialVisit: VisitData;
  onFormSubmit: (visit: VisitData) => void | Promise<string | void>;
};

const VisitForm = ({ initialVisit, onFormSubmit }: VisitFormProps) => {
  return (
    <EditForm
      formTitle="Add Visit"
      initialModel={initialVisit}
      onFormSubmit={onFormSubmit}
      formElements={[
        {
          elementComponentFactory: DateInputFactory,
          name: "date",
          label: "Date",
          constraint: NotEmpty
        },
        {
          name: "description",
          label: "Description",
          constraint: NotEmpty,
          elementComponentFactory: InputFactory
        }
      ]}
    />
  );
};

export default VisitForm;
