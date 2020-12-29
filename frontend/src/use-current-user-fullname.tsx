import { useMeQuery } from "generated/graphql-types";

export function useCurrentUserFullname() {
  const { loading, error, data } = useMeQuery();

  if (loading || error || !data) {
    return null;
  }

  return data.me.fullname;
}
