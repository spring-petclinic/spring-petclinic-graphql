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
      } mx-auto border-gray-100 border-4 rounded flex justify-between items-center p-4 mb-3.5`}
    >
      {children}
    </div>
  );
}
