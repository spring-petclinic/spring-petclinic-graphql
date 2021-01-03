import * as React from "react";

type HeadingProps = {
  children: React.ReactNode;
  level?: "2" | "3" | "4";
};

export default function Heading({ children, level = "2" }: HeadingProps) {
  switch (level) {
    case "2":
      return (
        <h2 className="pb-2 text-2xl font-metro font-semibold">{children}</h2>
      );
    case "3":
      return (
        <h3 className="pb-2 text-xl font-metro font-semibold">{children}</h3>
      );
    case "4":
      return <h4 className="pb-2 font-metro font-semibold">{children}</h4>;
  }

  return <>{children}</>;
}
