import * as React from "react";

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
}: ButtonProps) {
  //const class="hover:text-spr-white disabled:cursor-default disabled:"
  // const className =
  //   type === "primary"
  //     ? "bg-spr-white text-spr-black uppercase py-1.5 px-3.5 hover:bg-spr-green hover:text-spr-white font-medium font-semibold hover:border-spr-green border-spr-black border rounded disabled:cursor-default"
  //     : "bg-spr-black text-spr-white uppercase py-1 px-3.5 hover:bg-spr-green font-helvetica hover:border-spr-green border-spr-black border disabled:cursor-default ";
  const className = `Button--${type}`;
  return (
    <button onClick={onClick} className={className} disabled={disabled}>
      {children}
    </button>
  );
}
