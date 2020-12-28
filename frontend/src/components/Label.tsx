import * as React from "react";

type LabelProps = {
  children: React.ReactNode;
  type?: "error";
};
export default function Label({ children, type = "error" }: LabelProps) {
  return <p className="text-spr-red">{children}</p>;
}
