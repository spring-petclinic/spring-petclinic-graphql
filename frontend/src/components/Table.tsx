import * as React from "react";
import Heading from "./Heading";

type TableProps = {
  title?: string;
  labels?: string[];
  values: React.ReactNode[][];
};

export default function Table({ title, labels, values }: TableProps) {
  return (
    <>
      {title && <Heading>{title}</Heading>}
      <table className="mb-3.5 w-full">
        {labels && labels.length && (
          <thead>
            <tr>
              {labels.map((label, ix) => (
                <td key={ix} className="border-b py-3.5 pr-1 font-bold">
                  {label}
                </td>
              ))}
            </tr>
          </thead>
        )}
        <tbody>
          {values.map((row, ix) => (
            <tr key={ix}>
              {row.map((col, ix) => (
                <td key={ix} className="border-b py-3.5 pr-1">
                  {col}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
