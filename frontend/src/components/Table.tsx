import * as React from "react";
import Button from "./Button";
import Heading from "./Heading";
import Link from "./Link";
import Section from "./Section";

export default function Table() {
  return (
    <Section>
      <Heading>10 Owners found</Heading>
      <table className="w-full">
        <thead>
          <tr>
            <td className="border-b pr-1 py-3.5 font-bold">Name</td>
            <td className="border-b pr-1 py-3.5 font-bold">Address</td>
            <td className="border-b pr-1 py-3.5 font-bold">City</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="border-b pr-1 py-3.5">
              <Link href="fasdfd">Lorem</Link>
            </td>
            <td className="border-b pr-1 py-3.5">Ipsum</td>
            <td className="border-b pr-1 py-3.5">tra-la-la-la</td>
          </tr>
          <tr>
            <td className="border-b pr-1 py-3.5">Lorem</td>
            <td className="border-b pr-1 py-3.5">
              <Button type="secondary">Delete</Button>
            </td>
            <td className="border-b pr-1 py-3.5">
              <Button>Edit</Button>
            </td>
          </tr>
        </tbody>
      </table>
    </Section>
  );
}
