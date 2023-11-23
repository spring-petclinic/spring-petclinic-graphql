import * as React from "react";
import Heading from "./Heading";
import { useId } from "react";

type TableProps = {
  title?: string;
  actions?: React.ReactNode;
  labels?: string[];
  values: React.ReactNode[][];
};

export default function Table({ title, actions, labels, values }: TableProps) {
  const headingId = useId();
  return (
    <>
      {(title || actions) && (
        <div className={"flex justify-between"}>
          {title && <Heading id={headingId}>{title}</Heading>}
          {actions}
        </div>
      )}

      <table className="mb-3.5 w-full" aria-labelledby={headingId}>
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
