import * as React from "react";
import PageHeader from "./PageHeader";
import Nav from "./Nav";

type PageLayoutProps = {
  children?: React.ReactNode;
};

export default function PageLayout({ children }: PageLayoutProps) {
  return (
    <div>
      <Nav />
      <PageHeader>Animals</PageHeader>

      <main>
        <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">{children}</div>

        {/* <!-- /End replace --> */}
      </main>
    </div>
  );
}
