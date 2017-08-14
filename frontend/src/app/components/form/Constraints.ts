import { Constraint } from "./types";

export const NotEmpty: Constraint = {
  message: "Enter at least one character",
  validate: value => !!value && value.length > 0
};

export const Digits = (digits: number): Constraint => {
  const reg = new RegExp("^\\d{1," + digits + "}$");
  return {
    message: `Must be a number with at most ${digits} digits`,
    validate: value => !!value && value.match(reg) !== null
  };
};
