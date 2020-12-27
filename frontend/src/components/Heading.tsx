import * as React from "react";

type HeadingProps = {
  children: React.ReactNode;
  level?: "2" | "3";
};

export default function Heading({ children, level = "2" }: HeadingProps) {
  const className = "pb-2 text-xl font-metro font-semibold";

  return level === "2" ? (
    <h2 className={className}>{children}</h2>
  ) : (
    <h3 className={className}>{children}</h3>
  );
}
