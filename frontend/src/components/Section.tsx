import * as React from "react";

type SectionProps = {
  children: React.ReactNode;
};
export default function Section({ children }: SectionProps) {
  return <div className="px-4 pt-8 sm:px-0">{children}</div>;
}
