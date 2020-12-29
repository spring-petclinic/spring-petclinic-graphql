import { useApolloClient } from "@apollo/client";
import { useAuthToken } from "login/AuthTokenProvider";

export function useLogout() {
  const client = useApolloClient();
  const [, setAuthToken] = useAuthToken();

  return function logout() {
    setAuthToken(null);
    client.clearStore();
  };
}
