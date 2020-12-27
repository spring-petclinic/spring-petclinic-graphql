import * as React from 'react';
import { Link } from 'react-router-dom';

type LinkButtonProps = {
  to: string;
  children: React.ReactNode;
};

const LinkButton = ({ to, children }: LinkButtonProps) =>
  <Link to={to} className="btn btn-default">
    {children}
  </Link>;
export default LinkButton;
