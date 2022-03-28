import * as React from "react";
import PageHeader from "./PageHeader";
import { DefaultNavBar, NavBar } from "./Nav";

type PageLayoutProps = {
  children?: React.ReactNode;
  title: string;
  narrow?: boolean;
};

export default function PageLayout({
  children,
  title,
  narrow,
}: PageLayoutProps) {
  const maxWith = narrow ? "max-w-2xl" : "max-w-7xl";
  const className = `${maxWith} mx-auto py-6 sm:px-6 lg:px-8`;
  return (
    <div>
      <DefaultNavBar />
      <PageHeader>{title}</PageHeader>
      <main className={className}>{children}</main>
    </div>
  );
}

export function AnonymousPageLayout({
  children,
  title,
  narrow,
}: PageLayoutProps) {
  const maxWith = narrow ? "max-w-2xl" : "max-w-7xl";
  const className = `${maxWith} mx-auto py-6 sm:px-6 lg:px-8`;
  return (
    <div>
      <NavBar />
      <PageHeader>{title}</PageHeader>
      <main className={className}>{children}</main>
    </div>
  );
}
