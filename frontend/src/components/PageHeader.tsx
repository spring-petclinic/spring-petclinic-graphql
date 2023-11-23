import * as React from "react";

type PageHeaderProps = {
  children: React.ReactNode;
};

export default function PageHeader({ children }: PageHeaderProps) {
  return (
    <header className="bg-white shadow">
      <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
        <h1 className="text-3xl font-bold leading-tight text-spr-black">
          {children}
        </h1>
      </div>
    </header>
  );
}
