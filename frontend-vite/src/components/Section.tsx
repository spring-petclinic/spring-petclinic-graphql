import * as React from "react";

type SectionProps = {
  invert?: boolean;
  narrow?: boolean;
  children: React.ReactNode;
};
export default function Section({ children, invert, narrow }: SectionProps) {
  // const className="bg-gray-50"
  // narrow ? "max-w-2xl" : "max-w-7xl"
  let className = invert ? "bg-gray-100 p-4 mb-4" : "px-4 pb-8 sm:px-0";
  if (narrow) {
    className = className + " max-w-2xl mx-auto";
  }
  // className = narrow ? className + ""

  return <div className={className}>{children}</div>;
}
