import Button from "components/Button";
import ButtonBar from "components/ButtonBar";
import Heading from "components/Heading";
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

  function handleFindClick(formData: FindOwnerFormData) {
    findOwnersByLastName({
      variables: { lastName: formData.lastName },
    });
  }

  let resultTable = null;
  if (called && !loading && !error && data) {
    if (data.owners.length === 0) {
      resultTable = "No owners found";
    } else {
      const values = data.owners.map((owner) => [
        <Link to={`/owners/${owner.id}`}>{owner.lastName}</Link>,
        owner.firstName,
        owner.address,
        owner.city,
        owner.telephone,
      ]);
      resultTable = (
        <Table
          title={`${data.owners.length} owners found`}
          labels={["First name", "Last name", "Address", "City", "Telephone"]}
          values={values}
        />
      );
    }
  }

  console.log("DATA", data);

  console.log("errors", errors);
  return (
    <PageLayout title="Owners">
      <Heading>Search Owner</Heading>
      <Input
        ref={register({ required: true })}
        label="Last Name"
        name="lastName"
        error={errors.lastName && "Please enter a lastname (or part of)"}
      />
      <ButtonBar>
        <Button onClick={handleSubmit(handleFindClick)}>Find</Button>
      </ButtonBar>

      {resultTable}

      <Heading>New Owner</Heading>
      <p>You can add a new owner if you don't find an existing one.</p>
      <Button>Create Owner</Button>
    </PageLayout>
  );
}
