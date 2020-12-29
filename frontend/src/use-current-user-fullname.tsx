import { useMeQuery } from "generated/graphql-types";

export function useCurrentUser() {
  const { loading, error, data } = useMeQuery();

  if (loading || error || !data) {
    return { username: null, fullname: null };
  }

  return {
    fullname: data.me.fullname || null,
    username: data.me.username || null,
  };
}
