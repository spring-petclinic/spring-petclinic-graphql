import * as React from "react";

type LinkProps = {
  href: string;
  children: React.ReactNode;
};

export default function Link({ href, children }: LinkProps) {
  return (
    <a className="text-spr-blue hover:text-spr-gray-dark" href={href}>
      {children}
    </a>
  );
}
