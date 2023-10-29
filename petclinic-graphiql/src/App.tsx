import { createGraphiQLFetcher, Fetcher } from "@graphiql/toolkit";
import { GraphiQL } from "graphiql";
import "graphiql/graphiql.css";
import {useEffect, useMemo, useState} from "react";
import {graphqlApiUrl, graphqlWsApiUrl, pingApiUrl} from "./urls.ts";
import LoginForm from "./LoginForm.tsx";

import { createClient } from "graphql-ws";

type Login = { token: string; username: string};

type LoginVerificationState = {
  state: "pending"
} | {
  state: "verified",
  initialLogin: Login | null
}

export default function App() {
  const [loginVerificationState, setLoginVerificationState] = useState<LoginVerificationState>({state: "pending"});

  useEffect(() => {
    const initialToken = localStorage.getItem("petclinic.graphiql.token");
    const initialUsername = localStorage.getItem("petclinic.graphiql.username");

    if (!initialUsername || !initialToken) {
      localStorage.removeItem("petclinic.graphiql.token");
      localStorage.removeItem("petclinic.graphiql.username");

      setLoginVerificationState(({state: "verified", initialLogin: null}))
      return;
    }

    // call ping endpoint with initial token
    //   it it returns HTTP OK token is valid
    //   otherwise it's not valid anymore
    fetch(pingApiUrl, {
      headers: {
        "Authorization": `Bearer ${initialToken}`
      }
    }).then(res => {
      if (res.ok) {
        setLoginVerificationState({
          state: "verified",
          initialLogin: { token: initialToken, username: initialUsername }
        })

        return;
      }
      console.log("Token not valid anymore? Status from ping", res.status);
      setLoginVerificationState({
        state: "verified", initialLogin: null
      })
    })
  }, []);

  if (loginVerificationState.state === "pending") {
    return <h1>Verify login...</h1>
  }

  return <AppWithAuth initialLogin={loginVerificationState.initialLogin} />

}

type AppWithAuthProps = {
  initialLogin: Login|null
}

function AppWithAuth({initialLogin}: AppWithAuthProps) {
  const [currentLogin, setCurrentLogin] = useState<{
    token: string;
    username: string;
  } | null>(initialLogin);

  console.log("initialLogin", initialLogin);

  const [showToken, setShowToken] = useState(false);

  const fetcher: Fetcher | null = useMemo(() => {
    if (!currentLogin) {
      return null;
    }

    // When using only the `subscriptionUrl` paran in `createGraphiQLFetcher`
    // there is a runtime error in the prod build (probalbly bundling related)
    // so we create ower own client here
    // The code is inspired by the code that createClient uses internally
    //   node_modules/.pnpm/@graphiql+toolkit@0.9.1_@types+node@20.8.7_graphql-ws@5.14.1_graphql@16.8.1/node_modules/@graphiql/toolkit/src/create-fetcher/createFetcher.ts
    //   getWsFetcher
    const wsClient = createClient({
      url: `${graphqlWsApiUrl}?access_token=${currentLogin.token}`,
      connectionParams: {},
    });

    return createGraphiQLFetcher({
      url: graphqlApiUrl,
      wsClient,
      headers: {
        Authorization: `Bearer ${currentLogin.token}`,
      },
    });
  }, [currentLogin]);

  if (fetcher) {
    return (
      <>
        <div className={"loginInfo"}>
          <span>
            Logged in as <b>{currentLogin?.username}</b>
          </span>
          <button onClick={() => setShowToken(true)}>Show token</button>
          <button onClick={() => setCurrentLogin(null)}>Logout</button>
        </div>
        {showToken && (
          <div className={"tokenInfo"}>
            <b>Token</b>
            <textarea value={currentLogin?.token}></textarea>
            <div className={"tokenInfo--buttons"}>
              <button onClick={() => setShowToken(false)}>Hide</button>
              <button
                onClick={() =>
                  navigator.clipboard.writeText(
                    `Authorization: "Bearer ${currentLogin?.token}"`,
                  )
                }
              >
                Copy Auth Header
              </button>
            </div>
          </div>
        )}

        <GraphiQL fetcher={fetcher} />
      </>
    );
  }

  return <LoginForm onLogin={(token) => setCurrentLogin(token)} />;
}

