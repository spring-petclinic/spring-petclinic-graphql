import { useMeLazyQuery } from "generated/graphql-types";
import { useAuthToken } from "login/AuthTokenProvider";
import LoginPage from "login/LoginPage";
import React from "react";
import { Route, Switch } from "react-router-dom";
import WelcomePage from "WelcomePage";

function App() {
  const [token] = useAuthToken();
  const [queryMe, { called, loading, error }] = useMeLazyQuery();

  React.useEffect(() => {
    if (token) {
      queryMe();
    }
  }, [queryMe, token]);

  if (!token || error) {
    return <LoginPage />;
  }

  if (loading || !called) {
    return <h1>Verify token...</h1>;
  }

  return (
    <Switch>
      <Route path="/" exact>
        <WelcomePage />
      </Route>
    </Switch>
  );
}

export default App;
