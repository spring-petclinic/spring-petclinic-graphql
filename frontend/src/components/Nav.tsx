/* eslint-disable jsx-a11y/anchor-is-valid */
import { useAuthToken } from "login/AuthTokenProvider";
import * as React from "react";
import { NavLink as RouterNavLink } from "react-router-dom";
import { useCurrentUser } from "use-current-user-fullname";
import { useLogout } from "use-logout";
import Link from "./Link";

function NavLogo() {
  return (
    <div className="flex items-center">
      <div className="flex-shrink-0">
        <Link to="/">
          <img
            className="h-12"
            src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg"
            alt="Workflow"
          />
        </Link>
      </div>
    </div>
  );
}

type NavBarProps = {
  nav?: React.ReactElement;
  mobileMenu?: React.ReactElement;
};
export function NavBar({ nav, mobileMenu }: NavBarProps) {
  return (
    <nav className="bg-spr-green-light">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <NavLogo />
          {nav}
        </div>
      </div>
      {mobileMenu}
    </nav>
  );
}

type NavLinkProps = {
  to: string;
  children: React.ReactNode;
  exact?: boolean;
};
function NavLink({ children, exact, to }: NavLinkProps) {
  return (
    <RouterNavLink
      to={to}
      exact={exact}
      className="NavLink"
      activeClassName="NavLink--active"
    >
      {children}
    </RouterNavLink>
  );
}

export function DefaultNavBar() {
  const [authToken] = useAuthToken();
  const handleSignOut = useLogout();
  const { username, fullname } = useCurrentUser();
  const [profileMenuOpen, setProfileMenuOpen] = React.useState(false);

  return (
    <NavBar
      nav={
        <div className="hidden md:block">
          <div className="ml-10 flex space-x-4">
            {/* <a
              href="/"
              className="bg-spr-white text-spr-black px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green"
            >
              Home
            </a> */}

            <NavLink to="/" exact>
              Home
            </NavLink>
            <NavLink to="/owners">Owners</NavLink>
            <NavLink to="/vets">Veterinarians</NavLink>
            {/* 
            <a
              href="#"
              className="text-spr-black hover:bg-spr-white px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green-light  hover:border-spr-green"
            >
              Owners
            </a>
            <NavLink to="/owners">Owners</NavLink>

            <a
              href="#"
              className="text-spr-black hover:bg-spr-white px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green-light  hover:border-spr-green"
            >
              Veterinarians
            </a>
            <a
              href="#"
              className="text-spr-black hover:bg-spr-white px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green-light  hover:border-spr-green"
            >
              Specialities
            </a> */}
            <div className="ml-4 flex items-center md:ml-6">
              <div className="ml-3 relative ">
                <div className="">
                  <button
                    onClick={() => setProfileMenuOpen(!profileMenuOpen)}
                    className="max-w-xs rounded-full flex items-center text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-spr-gray-dark focus:ring-white"
                    id="user-menu"
                    aria-haspopup="true"
                  >
                    <span className="sr-only">Open user menu</span>
                    <img
                      className="h-8 w-8 rounded-full"
                      src={`http://localhost:9977/images/${username}.png?token=${authToken}`}
                      alt=""
                    />
                  </button>
                </div>
                {/* <!--
                Profile dropdown panel, show/hide based on dropdown state.

                Entering: "transition ease-out duration-100"
                  From: "transform opacity-0 scale-95"
                  To: "transform opacity-100 scale-100"
                Leaving: "transition ease-in duration-75"
                  From: "transform opacity-100 scale-100"
                  To: "transform opacity-0 scale-95"
              --> */}
                {profileMenuOpen && (
                  <div
                    className="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-spr-gray-dark ring-opacity-5 bg-spr-white"
                    role="menu"
                    aria-orientation="vertical"
                    aria-labelledby="user-menu"
                  >
                    <span className="block px-4 py-2 text-sm text-spr-black border-b cursor-pointer">
                      Signed in as <b>{fullname}</b>
                    </span>

                    <button
                      className="block px-4 py-2 text-sm text-spr-black hover:bg-gray-100 w-full text-left"
                      role="menuitem"
                      onClick={handleSignOut}
                    >
                      Sign out
                    </button>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      }
      mobileMenu={
        <div className="hidden md:hidden">
          <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
            <a
              href="#"
              className="bg-gray-900 text-white block px-3 py-2 rounded-md text-base font-medium"
            >
              Home
            </a>

            <a
              href="#"
              className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >
              Owners
            </a>

            <a
              href="#"
              className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >
              Veterinarians
            </a>

            <a
              href="#"
              className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >
              Specialities
            </a>

            <a
              href="#"
              className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >
              Reports
            </a>
          </div>
        </div>
      }
    />
  );
}
