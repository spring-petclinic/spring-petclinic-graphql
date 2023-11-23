import * as React from "react";

type InputProps = {
  action?: React.ReactNode;
  disabled?: boolean;
  error?: string | boolean | null;
  id?: string;
  label: string;
  name?: string;
  type?: "text" | "password" | "date";
};

const Input = React.forwardRef<HTMLInputElement, InputProps>(function Input(
  { action, disabled, error, id, label, name, type = "text", ...rest },
  ref,
) {
  return (
    <div className="w-full py-3.5">
      <div className="col-span-3 sm:col-span-2">
        <label className="block">
          {label}
          <div className="mt-1 flex rounded-md shadow-sm">
            <input
              ref={ref}
              type={type}
              name={name}
              id={id}
              disabled={disabled}
              className="focus:ring-3 flex-1 hover:border-spr-green focus:border-spr-green focus:ring-spr-green"
              {...rest}
            />
            {action}
          </div>
        </label>
        {typeof error === "string" && <p className="text-spr-red">{error}</p>}
      </div>
    </div>
  );
});

export default Input;
