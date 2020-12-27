import * as React from "react";

type ButtonProps = {
  onClick?(): void;
  type?: "primary" | "secondary";
  children: React.ReactNode;
};

export default function Button({
  onClick,
  children,
  type = "primary",
}: ButtonProps) {
  const className =
    type === "primary"
      ? "bg-spr-black text-spr-white uppercase py-1 px-3.5 hover:bg-spr-green font-helvetica hover:border-spr-green border-spr-black border-2"
      : "bg-spr-white text-spr-black uppercase py-1 px-3.5 hover:bg-spr-green font-helvetica hover:border-spr-green border-spr-black border-2";

  return (
    <button onClick={onClick} className={className}>
      {children}
    </button>
  );
}
