import * as React from "react";
import Heading from "./Heading";
import Section from "./Section";

type TableProps = {
  title: string;
  labels: string[];
  values: React.ReactNode[][];
};

export default function Table({ title, labels, values }: TableProps) {
  return (
    <Section>
      <Heading>{title}</Heading>
      <table className="w-full">
        <thead>
          <tr>
            {labels.map((label, ix) => (
              <td key={ix} className="border-b pr-1 py-3.5 font-bold">
                {label}
              </td>
            ))}
          </tr>
        </thead>
        <tbody>
          {values.map((row, ix) => (
            <tr key={ix}>
              {row.map((col, ix) => (
                <td key={ix} className="border-b pr-1 py-3.5">
                  {col}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </Section>
  );
}
