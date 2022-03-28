import * as React from "react";

type IAuthContext = {
  token: string | null;
  updateToken(token: string | null): void;
};

const AuthContext = React.createContext<IAuthContext>({
  token: null,
  updateToken() {},
});

type AuthContextProviderProps = {
  children: React.ReactNode;
};

export function AuthTokenProvider({ children }: AuthContextProviderProps) {
  const [token, setToken] = React.useState<string | null | undefined>(
    undefined
  );

  React.useEffect(() => {
    setToken(localStorage.getItem("petclinic.token") || null);
  }, []);

  function updateToken(newToken: string | null) {
    if (!newToken) {
      localStorage.removeItem("petclinic.token");
    } else {
      localStorage.setItem("petclinic.token", newToken);
    }

    setToken(newToken);
  }

  return token === undefined ? null : (
    <AuthContext.Provider value={{ token, updateToken }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuthToken() {
  const { token, updateToken } = React.useContext(AuthContext);
  return [token, updateToken] as const;
}
