import { createGraphiQLFetcher, Fetcher } from "@graphiql/toolkit";
import { GraphiQL } from "graphiql";
import "graphiql/graphiql.css";
import { useMemo, useState } from "react";
import { graphqlApiUrl, graphqlWsApiUrl } from "./urls.ts";
import LoginForm from "./LoginForm.tsx";

import { createClient } from "graphql-ws";

const initialToken = localStorage.getItem("petclinic.graphiql.token");
const initialUsername = localStorage.getItem("petclinic.graphiql.username");

const initialLogin =
  initialToken && initialUsername
    ? { token: initialToken, username: initialUsername }
    : null;

function App() {
  const [currentLogin, setCurrentLogin] = useState<{
    token: string;
    username: string;
  } | null>(initialLogin);

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

export default App;
