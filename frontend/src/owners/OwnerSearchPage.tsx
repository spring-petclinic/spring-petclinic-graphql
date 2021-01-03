import Button from "components/Button";
import Input from "components/Input";
import Link from "components/Link";
import PageLayout from "components/PageLayout";
import Table from "components/Table";
import { useFindOwnerByLastNameLazyQuery } from "generated/graphql-types";
import * as React from "react";
import { useForm } from "react-hook-form";

type FindOwnerFormData = { lastName: string };

export default function OwnersPage() {
  const [
    findOwnersByLastName,
    { loading, data, error, called },
  ] = useFindOwnerByLastNameLazyQuery();
  const { register, handleSubmit, errors } = useForm<FindOwnerFormData>({});

  function handleFindClick({ lastName }: FindOwnerFormData) {
    findOwnersByLastName({
      variables: lastName ? { lastName } : { lastName: null },
    });
  }

  let resultTable = null;
  if (called && !loading && !error && data) {
    if (data.owners.length === 0) {
      resultTable = <div className="max-w-2xl mx-auto">No owners found</div>;
    } else {
      const values = data.owners.map((owner) => [
        <Link to={`/owners/${owner.id}`}>{owner.lastName}</Link>,
        owner.firstName,
        owner.address,
        owner.city,
        owner.telephone,
        owner.pets.map((pet) => pet.name).join(", "),
      ]);
      resultTable = (
        <div className="mt-8 border-4 border-gray-100 p-4">
          <Table
            title={`${data.owners.length} owners found`}
            labels={[
              "First name",
              "Last name",
              "Address",
              "City",
              "Telephone",
              "Pets",
            ]}
            values={values}
          />
        </div>
      );
    }
  }

  console.log("DATA", data);

  console.log("errors", errors);
  const searchButton = (
    <Button onClick={handleSubmit(handleFindClick)}>Find</Button>
  );
  return (
    <PageLayout title="Search Owner">
      <div className="max-w-2xl mx-auto">
        <div className="flex">
          <Input
            ref={register}
            label="Last Name"
            name="lastName"
            action={searchButton}
          />
        </div>
      </div>
      <div className="mb-8">{resultTable}</div>

      <div className="max-w-2xl mx-auto border-gray-100 border-4 rounded flex justify-between items-center p-4">
        <p>You can add a new owner if you don't find an existing one</p>
        <Button>Create Owner</Button>
      </div>
    </PageLayout>
  );
}
