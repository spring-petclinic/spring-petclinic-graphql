import * as React from "react";

type HeadingProps = {
  children: React.ReactNode;
  level?: "2" | "3" | "4";
  id?: string;
};

export default function Heading({ children, id, level = "2" }: HeadingProps) {
  switch (level) {
    case "2":
      return (
        <h2 id={id} className="pb-2 font-metro text-2xl font-semibold">
          {children}
        </h2>
      );
    case "3":
      return (
        <h3 id={id} className="pb-2 font-metro text-xl font-semibold">
          {children}
        </h3>
      );
    case "4":
      return (
        <h4 id={id} className="pb-2 font-metro font-semibold">
          {children}
        </h4>
      );
  }

  return <>{children}</>;
}
