// ------------------------------------ REACT ------------------------------------
export type ReactFunctionOrComponentClass<P> = React.ComponentClass<P> | React.StatelessComponent<P>;

// ------------------------------------ ERROR ------------------------------------
export type FieldError = {
  field: string;
  message: string;
};

export type FieldErrors = {
  [index: string]: FieldError;
};

export type FormErrors = {
  fieldErrors?: FieldErrors;
  globalError?: string | null;
};

// ------------------------------------ FORM --------------------------------------
export type Constraint = {
  message: string;
  validate: (value: any) => boolean;
};

export type InputChangeHandler = (name: string, value: string) => void;

export type SelectOption = {
  id: string | number;
  name: string;
};

// ----------------- Form Structure -------------------------------------
export type ElementComponentFactoryFn = (
  element: FormElement,
  object: FormModel,
  fieldError: FieldError | null,
  onInputChange: any,
  onBlur: any
) => React.ReactNode;

export type FormElement = {
  label: string;
  name: string;
  constraint: Constraint;
  elementComponentFactory: ElementComponentFactoryFn;
};

export type SelectFormElement = FormElement & {
	options: SelectOption[]
}

// ---------------------------------------------------------------------------------------
export type FormModel = {
  [key: string]: any;
};
