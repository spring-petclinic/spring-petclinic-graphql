import * as React from "react";

import { FormElement, FormModel, FieldError, SelectOption, SelectFormElement } from "../types";

import Input from "./elements/Input";
import DateInput from "./elements/DateInput";
import SelectInput from "./elements/SelectInput";

export function InputFactory(
  element: FormElement,
  object: FormModel,
  fieldError: FieldError | null,
  onInputChange: any,
  onBlur: any
) {
  return (
    <Input
      key={element.name}
      object={object}
      fieldError={fieldError}
      constraint={element.constraint}
      label={element.label}
      name={element.name}
      onChange={onInputChange}
      onBlur={onBlur}
    />
  );
}

export function DateInputFactory(
  element: FormElement,
  object: FormModel,
  fieldError: FieldError | null,
  onInputChange: any,
  onBlur: any
) {
  return (
    <DateInput
      key={element.name}
      object={object}
      fieldError={fieldError}
      label={element.label}
      name={element.name}
      onChange={onInputChange}
      onBlur={onBlur}
    />
  );
}

export function SelectInputFactory(
  element: SelectFormElement,
  object: FormModel,
  fieldError: FieldError | null,
  onInputChange: any,
	onBlur: any,
) {
  return (
    <SelectInput
      key={element.name}
			options={element.options}
      object={object}
      fieldError={fieldError}
      label={element.label}
      name={element.name}
      onChange={onInputChange}
      onBlur={onBlur}
    />
  );
}

