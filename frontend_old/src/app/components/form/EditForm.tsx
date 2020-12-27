import * as React from "react";
import { InputChangeHandler, FieldErrors, FieldError, Constraint, FormElement, FormModel } from "./types";

type EditFormProps<MODEL extends FormModel> = {
  initialModel: MODEL;
  formTitle: string;
  formElements: FormElement[];
  onFormSubmit: (model: MODEL) => void | Promise<string | void>;
  copyModel?: (model: MODEL) => MODEL;
};

type VisitedFieldsMap<MODEL> = { [P in keyof MODEL]?: boolean };
type EditFormState<MODEL extends FormModel> = {
  model: MODEL;
  visitedFields: VisitedFieldsMap<MODEL>;
  globalFormError?: string | null;
};

export default class EditForm<MODEL extends FormModel> extends React.Component<EditFormProps<MODEL>, EditFormState<MODEL>> {
  constructor(props: EditFormProps<MODEL>) {
    super(props);
    this.onInputChange = this.onInputChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onBlur = this.onBlur.bind(this);

    const { copyModel } = props;

    this.state = {
      model: copyModel ? copyModel(props.initialModel) : props.initialModel,
      visitedFields: {}
    };
  }

  onSubmit(event: React.SyntheticEvent<HTMLButtonElement>) {
    event.preventDefault();
    const { model } = this.state;
    const { onFormSubmit } = this.props;

    const result = onFormSubmit(model);
    if (result instanceof Promise) {
      result.catch(error => this.setState({ globalFormError: error }));
    }
  }

  onInputChange(name: string, value: string) {
    const { model, visitedFields } = this.state as any;
    this.setState({
      // update the model with new input
      model: { ...model, [name]: value },

      // track that the field has been visited
      visitedFields: { ...visitedFields, [name]: true },

      // reset global form error
      globalFormError: null
    });
  }

  onBlur(name: string) {
    const { visitedFields } = this.state;
    this.setState({
      // track that the field has been visited
      visitedFields: { ...visitedFields as {}, [name]: true },

      // reset global form error
      globalFormError: null
    });
  }

  validateCurrentModel(): FieldErrors {
    const { model, visitedFields } = this.state;
    const { formElements } = this.props;

    const result: FieldErrors = {};
    formElements.forEach(({ name, constraint }) => {
      if (visitedFields[name]) {
        if (!constraint.validate(model[name])) {
          result[name] = {
            field: name,
            message: constraint.message
          };
        }
      }
    });

    return result;
  }

  render() {
    const { model, visitedFields, globalFormError } = this.state;
    const { formTitle, formElements } = this.props;
    const fieldErrors = this.validateCurrentModel();

    return (
      <span>
        <h2>
          {formTitle}
        </h2>
        <form className="form-horizontal" method="POST">
          <div className="form-group has-feedback">
            {formElements.map(element =>
              element.elementComponentFactory(element, model, fieldErrors[element.name] || null, this.onInputChange, this.onBlur)
            )}
          </div>
          <div className="form-group">
            <div className="col-sm-offset-2 col-sm-10">
              <button className="btn btn-default" type="submit" onClick={this.onSubmit}>
                {formTitle}
              </button>
            </div>
          </div>
          {globalFormError &&
            <div className="alert alert-danger" role="alert">
              {globalFormError}
            </div>}
        </form>
      </span>
    );
  }
}
