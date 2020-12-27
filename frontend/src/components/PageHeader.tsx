import * as React from "react";

type PageHeaderProps = {
  children: React.ReactNode;
};

export default function PageHeader({ children }: PageHeaderProps) {
  return (
    <header className="bg-white shadow">
      <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <h1 className="text-3xl font-bold leading-tight text-spr-black">
          {children}
        </h1>
      </div>
    </header>
  );
}
