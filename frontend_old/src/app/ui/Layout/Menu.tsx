import * as React from "react";

import { Route, Link } from "react-router-dom";

const MenuItem = ({
  to,
  label,
  activeOnlyWhenExact = false,
  children
}: {
  activeOnlyWhenExact?: boolean;
  to: string;
  label: string;
  children: React.ReactNode;
}) =>
  <Route
    path={to}
    exact={activeOnlyWhenExact}
    children={({ match }) =>
      <li className={match ? "active" : ""}>
        <Link to={to} title={label}>
          {children}
        </Link>
      </li>}
  />;

const Menu = () =>
  <nav className="navbar navbar-default" role="navigation">
    <div className="container">
      <div className="navbar-header">
        <Link className="navbar-brand" to="/">
          <span />
        </Link>
        <button type="button" className="navbar-toggle" data-toggle="collapse" data-target="#main-navbar">
          <span className="icon-bar" />
          <span className="icon-bar" />
          <span className="icon-bar" />
        </button>
      </div>
      <div className="navbar-collapse collapse" id="main-navbar">
        <ul className="nav navbar-nav navbar-right">
          <MenuItem activeOnlyWhenExact to="/" label="home page">
            <span className="glyphicon glyphicon-home" aria-hidden="true" />&nbsp;
            <span>Home</span>
          </MenuItem>

          <MenuItem to="/owners" label="owners">
            <span className="glyphicon glyphicon-user" aria-hidden="true" />&nbsp;
            <span>Owners</span>
          </MenuItem>

          <MenuItem to="/vets" label="veterinarians">
            <span className="glyphicon glyphicon-th-list" aria-hidden="true" />&nbsp;
            <span>Veterinarians</span>
          </MenuItem>
          <MenuItem to="/specialties" label="Specialties">
            <span className="glyphicon glyphicon-scissors" aria-hidden="true" />&nbsp;
            <span>Specialties</span>
          </MenuItem>
        </ul>
      </div>
    </div>
  </nav>;

export default Menu;
