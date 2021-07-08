import Button from "components/Button";
import Input from "components/Input";
import Link from "components/Link";
import PageLayout from "components/PageLayout";
import Table from "components/Table";
import { useFindOwnerByLastNameLazyQuery } from "generated/graphql-types";
import * as React from "react";
import { useForm } from "react-hook-form";
import Paginator from "./Paginator";

type FindOwnerFormData = { lastName: string };

export default function OwnersPage() {
  const [
    findOwnersByLastName,
    { loading, data, error, called, refetch },
  ] = useFindOwnerByLastNameLazyQuery();
  const { register, handleSubmit } = useForm<FindOwnerFormData>({});

  function handleFindClick({ lastName }: FindOwnerFormData) {
    findOwnersByLastName({
      variables: lastName ? { lastName, page: 0 } : { lastName: null, page: 0 },
    });
  }

  function handlePageClick(newPageNumber: number) {
    if (refetch) {
      refetch({
        page: newPageNumber,
      });
    }
  }

  let resultTable = null;
  if (called && !loading && !error && data) {
    if (data.owners.owners.length === 0) {
      resultTable = <div className="max-w-2xl mx-auto">No owners found</div>;
    } else {
      const values = data.owners.owners.map((owner) => [
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
            title={`${data.owners.pageInfo.ownersCount} owners found`}
            labels={[
              "Last name",
              "First name",
              "Address",
              "City",
              "Telephone",
              "Pets",
            ]}
            values={values}
          />
          <Paginator
            pageInfo={data.owners.pageInfo}
            onPageClick={handlePageClick}
          />
        </div>
      );
    }
  }

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
    </PageLayout>
  );
}
