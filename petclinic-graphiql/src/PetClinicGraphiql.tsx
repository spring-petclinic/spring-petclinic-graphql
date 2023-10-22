import { Fetcher } from "@graphiql/toolkit";
import { GraphiQL } from "graphiql";

type PetClinicGraphiqlProps = {
  fetcher: Fetcher;
};

export default function PetClinicGraphiql({ fetcher }: PetClinicGraphiqlProps) {
  return <GraphiQL fetcher={fetcher} />;
}
