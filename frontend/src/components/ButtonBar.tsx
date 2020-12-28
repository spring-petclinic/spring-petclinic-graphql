import * as React from "react";

type ButtonBarProps = { children: React.ReactNode };

export default function ButtonBar({ children }: ButtonBarProps) {
  return (
    <div className="py-3.5 space-x-4 flex flex-row justify-end">{children}</div>
  );
}
