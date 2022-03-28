import * as React from "react";

type LabelProps = {
  children: React.ReactNode;
  type?: "error" | "info";
};
export default function Label({ children, type = "error" }: LabelProps) {
  return type === "error" ? (
    <p className="text-spr-red">{children}</p>
  ) : (
    <p>{children}</p>
  );
}
