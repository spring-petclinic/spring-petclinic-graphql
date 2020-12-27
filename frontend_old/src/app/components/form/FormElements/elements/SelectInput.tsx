import * as React from "react";

import { FieldError, InputChangeHandler, SelectOption } from "../../types";

import FieldFeedbackPanel from "./FieldFeedbackPanel";

export default ({
  object,
  fieldError,
  name,
  label,
  options,
	onChange,
	onBlur
}: {
  object: any;
  fieldError: FieldError | null;
  name: string;
  label: string;
  options: SelectOption[];
	onChange: InputChangeHandler;
	onBlur: (x: string) => void;
}) => {
  const handleOnChange = (event: React.SyntheticEvent<HTMLSelectElement>) => {
		const value = event.currentTarget.value;
    onChange(name, value);
  };

  const selectedValue = object[name] || "";
  const valid = !fieldError && selectedValue !== "";

  const cssGroup = `form-group ${fieldError ? "has-error" : ""}`;

  return (
    <div className={cssGroup}>
      <label className="col-sm-2 control-label">
        {label}
      </label>
      <div className="col-sm-10">
        <select size={5} className="form-control" name={name} onChange={handleOnChange} onBlur={e => onBlur(e.currentTarget.name)} value={selectedValue}>
          {options.map(option =>
            <option key={option.id} value={option.id}>
              {option.name}
            </option>
          )}
        </select>
        <FieldFeedbackPanel valid={valid} fieldError={fieldError} />
      </div>
    </div>
  );
};
