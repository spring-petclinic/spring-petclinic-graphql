import { createGraphiQLFetcher, Fetcher } from "@graphiql/toolkit";
import { GraphiQL } from "graphiql";
import "graphiql/graphiql.css";
import { useMemo, useState } from "react";
import { graphqlApiUrl, graphqlWsApiUrl } from "./urls.ts";
import LoginForm from "./LoginForm.tsx";

const wsHost = window.location.href
  .replace("https", "wss")
  .replace("http", "ws");

function App() {
  const [currentLogin, setCurrentLogin] = useState<{
    token: string;
    username: string;
  } | null>(null);

  const [showToken, setShowToken] = useState(false);

  const fetcher: Fetcher | null = useMemo(() => {
    if (!currentLogin) {
      return null;
    }

    const subscriptionUrl = `${graphqlWsApiUrl}`;
    console.log("subscriptionUrl", subscriptionUrl);

    return createGraphiQLFetcher({
      url: graphqlApiUrl,
      wsConnectionParams: {
        Authorization: `Bearer ${currentLogin.token}`,
      },
      subscriptionUrl,
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
