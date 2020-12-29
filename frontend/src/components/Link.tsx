import * as React from "react";
import { Link as RouterLink } from "react-router-dom";

type LinkProps = {
  to: string;
  children: React.ReactNode;
};

export default function Link({ to, children }: LinkProps) {
  return (
    <RouterLink className="text-spr-blue hover:text-spr-gray-dark" to={to}>
      {children}
    </RouterLink>
  );
}
