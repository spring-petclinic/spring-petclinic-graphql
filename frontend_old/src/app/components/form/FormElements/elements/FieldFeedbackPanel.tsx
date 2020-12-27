import * as React from "react";

import { FieldError } from "../../types";

const FieldFeedbackPanel = ({ valid, fieldError }: { valid: boolean; fieldError?: FieldError | null }) => {
  if (valid) {
    return <span className="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" />;
  }

  if (fieldError) {
    return (
      <span>
        <span className="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true" />
        <span className="help-inline">
          {fieldError.message}
        </span>
      </span>
    );
  }

  return null;
};

export default FieldFeedbackPanel;
