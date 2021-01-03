import * as React from "react";

type SectionProps = {
  invert?: boolean;
  children: React.ReactNode;
};
export default function Section({ children, invert }: SectionProps) {
  // const className="bg-gray-50"
  const className = invert ? "bg-gray-100 p-4 mb-4" : "px-4 pb-8 sm:px-0";
  return <div className={className}>{children}</div>;
}
