import * as React from "react";
import clsx from "clsx";

type ButtonProps = {
  children: React.ReactNode;
  disabled?: boolean;
  onClick?(): void;
  type?: "primary" | "secondary" | "link";
};

export default function Button({
  children,
  disabled,
  onClick,
  type = "primary",
  ...props
}: ButtonProps) {
  const className = clsx(
    type === "primary" &&
      `rounded border border-spr-green bg-spr-green px-3.5 py-1.5 font-medium uppercase text-spr-white hover:bg-spr-green-dark
      disabled:cursor-default disabled:border-spr-green-light disabled:bg-spr-green-light disabled:text-gray-400
      `,
    type === "secondary" &&
      "rounded border border-gray-500 bg-gray-50 px-3.5 py-1.5 font-medium uppercase text-spr-black hover:border-spr-green hover:bg-spr-green-dark hover:text-spr-white",
    type === "link" &&
      "px-3.5 py-1.5 font-bold uppercase text-spr-blue hover:bg-spr-white hover:underline",
  );

  //const class="hover:text-spr-white disabled:cursor-default disabled:"
  // const className =
  //   type === "primary"
  //     ? "bg-spr-white text-spr-black uppercase py-1.5 px-3.5 hover:bg-spr-green hover:text-spr-white font-medium font-semibold hover:border-spr-green border-spr-black border rounded disabled:cursor-default"
  //     : "bg-spr-black text-spr-white uppercase py-1 px-3.5 hover:bg-spr-green font-helvetica hover:border-spr-green border-spr-black border disabled:cursor-default ";
  return (
    <button
      {...props}
      onClick={onClick}
      className={className}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
