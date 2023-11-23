import { useForm } from "react-hook-form";
import Button from "@/components/Button";
import Input from "@/components/Input";
import PageLayout from "@/components/PageLayout";
import Table from "@/components/Table";
import {
  FindOwnerByLastNameQueryVariables,
  OrderDirection,
  useFindOwnerByLastNameLazyQuery,
} from "@/generated/graphql-types";
import Link from "@/components/Link.tsx";
import ButtonBar from "@/components/ButtonBar.tsx";
import { useState } from "react";
import { filterNull } from "@/utils.ts";

type FindOwnerFormData = { lastName: string };

export default function OwnersPage() {
  const [
    findOwnersByLastName,
    { loading, data, error, called, fetchMore, refetch },
  ] = useFindOwnerByLastNameLazyQuery();
  const { register, handleSubmit, getValues } = useForm<FindOwnerFormData>({});
  const [orderBy, setOrderBy] = useState<"ASC" | "DESC">("ASC");

  function handleFindClick() {
    search(orderBy);
  }

  function search(orderBy: "ASC" | "DESC") {
    const { lastName }: FindOwnerFormData = getValues();
    console.log("lastname", lastName);
    console.log("orderby", orderBy);
    const dir = orderBy === "ASC" ? OrderDirection.Asc : OrderDirection.Desc;
    const variables: FindOwnerByLastNameQueryVariables = lastName
      ? { lastName, after: null, dir }
      : { lastName: null, after: null, dir };

    if (!called) {
      findOwnersByLastName({ variables });
    } else {
      refetch(variables);
    }
  }

  function handleOrderChange(newOrder: "ASC" | "DESC") {
    setOrderBy(newOrder);
    search(newOrder);
  }

  function handleFetchMore() {
    fetchMore({
      variables: {
        after: data?.owners.pageInfo.endCursor,
      },
    });
  }

  let resultTable = null;
  if (called && !loading && !error && data) {
    if (data.owners.edges.length === 0) {
      resultTable = <div className="mx-auto max-w-2xl">No owners found</div>;
    } else {
      const values = data.owners.edges
        .filter(filterNull)
        .map(({ node: owner }) => [
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
            title={`Owners`}
            actions={
              <div>
                <Button
                  type="primary"
                  aria-label={"Order owners by lastname, ascending"}
                  onClick={() => {
                    handleOrderChange("ASC");
                  }}
                  disabled={orderBy === "ASC"}
                >
                  Asc
                </Button>
                <Button
                  type="primary"
                  aria-label={"Order owners by lastname, descending"}
                  onClick={() => {
                    handleOrderChange("DESC");
                  }}
                  disabled={orderBy === "DESC"}
                >
                  Desc
                </Button>
              </div>
            }
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
          <ButtonBar align={"center"}>
            <Button
              disabled={!data.owners.pageInfo.hasNextPage}
              onClick={handleFetchMore}
            >
              Load more
            </Button>
          </ButtonBar>
        </div>
      );
    }
  }

  return (
    <PageLayout title="Search Owner">
      <div className="mx-auto max-w-2xl">
        <div className="flex">
          <Input
            {...register("lastName")}
            label="Last Name"
            action={
              <Button onClick={handleSubmit(handleFindClick)}>Find</Button>
            }
          />
        </div>
      </div>
      <div className="mb-8">{resultTable}</div>
    </PageLayout>
  );
}
