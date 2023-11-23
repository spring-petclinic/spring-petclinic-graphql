import * as React from "react";

type SelectOption = {
  value: string | number;
  label: string;
};

type SelectProps = {
  defaultValue?: string | number;
  disabled?: boolean;
  error?: string | boolean | null;
  id?: string;
  label: string;
  multiple?: boolean;
  options: SelectOption[];
};

const Select = React.forwardRef<HTMLSelectElement, SelectProps>(function Input(
  { defaultValue, disabled, error, id, label, multiple, options, ...rest },
  ref,
) {
  return (
    <div className="w-full py-3.5">
      <div className="col-span-3 sm:col-span-2">
        <label className="block">
          {label}
          <div className="mt-1 flex rounded-md shadow-sm">
            <select
              className="w-full"
              defaultValue={defaultValue}
              id={id}
              ref={ref}
              disabled={disabled}
              multiple={multiple}
              {...rest}
            >
              {options.map((option) => (
                <option key={option.value} value={option.value}>
                  {option.label}
                </option>
              ))}
            </select>
          </div>
        </label>
        {typeof error === "string" && <p className="text-spr-red">{error}</p>}
      </div>
    </div>
  );
});

export default Select;
