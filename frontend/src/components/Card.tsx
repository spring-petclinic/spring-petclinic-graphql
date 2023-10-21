import * as React from "react";

type CardProps = {
  children: React.ReactNode;
  fullWidth?: boolean;
};
export default function Card({ children, fullWidth }: CardProps) {
  return (
    <div
      className={`${
        fullWidth ? "" : "max-w-2xl"
      } mx-auto mb-3.5 flex items-center justify-between rounded border-4 border-gray-100 p-4`}
    >
      {children}
    </div>
  );
}
