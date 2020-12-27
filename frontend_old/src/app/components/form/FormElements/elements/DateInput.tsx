import * as React from "react";
import ReactDatePicker from "react-datepicker";
import * as moment from "moment";

import { FieldError, InputChangeHandler } from "../../types";

import FieldFeedbackPanel from "./FieldFeedbackPanel";

const DateInput = ({
  object,
  fieldError,
  name,
  label,
	onChange,
	onBlur
}: {
  object: any;
  fieldError: FieldError | null;
  name: string;
  label: string;
	onChange: InputChangeHandler;
	onBlur: (name: string) => void;
}) => {
  const handleOnChange = (value: any) => {
    const dateString = value ? value.format("YYYY/MM/DD") : null;
    onChange(name, dateString);
  };

  const selectedValue = object[name] ? moment(object[name], "YYYY/MM/DD") : null;
  const valid = !fieldError && selectedValue != null;

  const cssGroup = `form-group ${fieldError ? "has-error" : ""}`;

  return (
    <div className={cssGroup}>
      <label className="col-sm-2 control-label">
        {label}
      </label>

      <div className="col-sm-10">
        <ReactDatePicker selected={selectedValue} onChange={handleOnChange} className="form-control" dateFormat="YYYY-MM-DD"
				onBlur={e => onBlur(e.currentTarget.name)}/>
        <span className="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" />
        <FieldFeedbackPanel valid={valid} fieldError={fieldError} />
      </div>
    </div>
  );
};
export default DateInput;
