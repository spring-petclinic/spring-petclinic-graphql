import * as React from "react";

import Menu from "./Menu";
import Footer from "./Footer";

type LayoutProps = {
  children: React.ReactNode;
};

const Layout = ({ children }: LayoutProps) =>
  <div>
    <Menu />
    <div className="container-fluid">
      <div className="container xd-container">
        {children}
      </div>
      <div>
        <Footer />
      </div>
    </div>
  </div>;

export default Layout;
