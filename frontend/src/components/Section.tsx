import * as React from "react";
import clsx from "clsx";

type SectionProps = {
  invert?: boolean;
  narrow?: boolean;
  children: React.ReactNode;
};
export function Section({ children, invert, narrow, ...props }: SectionProps) {
  const className = clsx(
    invert ? "mb-4 bg-gray-100 p-4" : "px-4 pb-8 sm:px-0",
    narrow && "mx-auto max-w-2xl",
  );

  return (
    <section className={className} {...props}>
      {children}
    </section>
  );
}

type SectionHeadingProps = {
  children: React.ReactNode;
};

export function SectionHeading({ children }: SectionHeadingProps) {
  return (
    <div className="mb-2 items-baseline justify-between border-b-4 border-spr-white pb-2 md:flex">
      {children}
    </div>
  );
}
