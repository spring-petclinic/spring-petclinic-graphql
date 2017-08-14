import * as React from "react";

import { Constraint, FieldError, InputChangeHandler } from "../../types";

import FieldFeedbackPanel from "./FieldFeedbackPanel";

const NoConstraint: Constraint = {
  message: "",
  validate: v => true
};

type InputProps = {
  object: any;
  fieldError: FieldError | null;
  name: string;
  constraint: Constraint;
  label: string;
  onChange: InputChangeHandler;
  onBlur: (name: string) => void;
};

const Input = ({ object, fieldError, name, constraint = NoConstraint, label, onChange, onBlur }: InputProps) => {
  const handleOnChange = (event: React.SyntheticEvent<HTMLInputElement>) => {
    const { value } = event.currentTarget;
    onChange(name, value);
  };

  const value = object[name];
  const valid = !fieldError && value !== null && value !== undefined;

  const cssGroup = `form-group ${fieldError ? "has-error" : ""}`;

  return (
    <div className={cssGroup}>
      <label className="col-sm-2 control-label">
        {label}
      </label>

      <div className="col-sm-10">
        <input
          type="text"
          name={name}
          className="form-control"
          value={value}
          onChange={handleOnChange}
          onBlur={e => onBlur(e.currentTarget.name)}
        />

        <FieldFeedbackPanel valid={valid} fieldError={fieldError} />
      </div>
    </div>
  );
};

export default Input;
