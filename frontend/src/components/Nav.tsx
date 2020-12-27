import * as React from "react";

export default function Nav() {
  return (
    <nav className="bg-spr-green-light">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <img
                className="h-12"
                src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg"
                alt="Workflow"
              />
            </div>
          </div>
          <div className="hidden md:block">
            <div className="ml-10 flex items-baseline space-x-4">
              <a
                href="#"
                className="bg-spr-white text-spr-black px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green"
              >
                Home
              </a>

              <a
                href="#"
                className="text-spr-black hover:bg-spr-white px-4 py-2 h-12 text-sm font-bold uppercase border-t-4 border-spr-green-light  hover:border-spr-green"
              >
                Owners
              </a>

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
              </a>
            </div>
          </div>
        </div>
      </div>

      {/* <!--
  Mobile menu, toggle classes based on menu state.

  Open: "block", closed: "hidden"
--> */}
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
    </nav>
  );
}
