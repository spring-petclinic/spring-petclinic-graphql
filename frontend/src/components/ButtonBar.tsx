import * as React from "react";

type ButtonBarProps = {
  align?: "left" | "right" | "center";
  children: React.ReactNode;
};

export default function ButtonBar({
  align = "right",
  children,
}: ButtonBarProps) {
  const className = `py-3.5 space-x-4 flex flex-row ${
    align === "right"
      ? "justify-end"
      : align === "center"
      ? "justify-center"
      : ""
  }`;
  return <div className={className}>{children}</div>;
}
