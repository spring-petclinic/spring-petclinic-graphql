import { createGraphiQLFetcher, Fetcher } from "@graphiql/toolkit";
import { GraphiQL } from "graphiql";
import "graphiql/graphiql.css";
import { useEffect, useMemo, useState } from "react";
import { graphqlApiUrl, graphqlWsApiUrl, pingApiUrl } from "./urls.ts";
import LoginForm from "./LoginForm.tsx";

import { createClient } from "graphql-ws";

type Login = { token: string; username: string };

type LoginVerificationState =
  | {
      state: "pending";
    }
  | {
      state: "verified";
      initialLogin: Login | null;
    };

export default function App() {
  const [loginVerificationState, setLoginVerificationState] =
    useState<LoginVerificationState>({ state: "pending" });

  useEffect(() => {
    const initialToken = localStorage.getItem("petclinic.graphiql.token");
    const initialUsername = localStorage.getItem("petclinic.graphiql.username");

    if (!initialUsername || !initialToken) {
      localStorage.removeItem("petclinic.graphiql.token");
      localStorage.removeItem("petclinic.graphiql.username");

      setLoginVerificationState({ state: "verified", initialLogin: null });
      return;
    }

    // call ping endpoint with initial token:
    //   - if it returns HTTP OK, token is valid
    //   - otherwise it's not valid anymore, and user has to login again
    fetch(pingApiUrl, {
      headers: {
        Authorization: `Bearer ${initialToken}`,
      },
    }).then((res) => {
      if (res.ok) {
        setLoginVerificationState({
          state: "verified",
          initialLogin: { token: initialToken, username: initialUsername },
        });

        return;
      }
      console.log("Token not valid anymore? Status from ping", res.status);
      setLoginVerificationState({
        state: "verified",
        initialLogin: null,
      });
    });
  }, []);

  if (loginVerificationState.state === "pending") {
    return <h1>Verify login...</h1>;
  }

  return <AppWithAuth initialLogin={loginVerificationState.initialLogin} />;
}

type AppWithAuthProps = {
  initialLogin: Login | null;
};

function AppWithAuth({ initialLogin }: AppWithAuthProps) {
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

        <GraphiQL
          fetcher={fetcher}
          defaultQuery={`
#    _____                 _      ____  _        _____     _    _____ _ _       _      
#   / ____|               | |    / __ \\| |      |  __ \\   | |  / ____| (_)     (_)     
#  | |  __ _ __ __ _ _ __ | |__ | |  | | |      | |__) |__| |_| |    | |_ _ __  _  ___ 
#  | | |_ | '__/ _\` | '_ \\| '_ \\| |  | | |      |  ___/ _ \\ __| |    | | | '_ \\| |/ __|
#  | |__| | | | (_| | |_) | | | | |__| | |____  | |  |  __/ |_| |____| | | | | | | (__ 
#   \\_____|_|  \\__,_| .__/|_| |_|\\___\\_\\______| |_|   \\___|\\__|\\_____|_|_|_| |_|_|\\___|
#                   | |                                                                
#                   |_|                                                                          
          
          
# Some sample queries:

# Username of currently logged in user:
query Me { me { username } }

# Find first to owners whose name starts with "d"
query TwoOwners {
  owners(
    first: 2
    filter: { lastName: "d" }
    order: [{ field: lastName }, { field: firstName, direction: DESC }]
  ) {
    edges {
      cursor
      node {
        id
        firstName
        lastName
        pets {
          id
          name
        }
      }
    }
    pageInfo {
      hasNextPage
      endCursor
    }
  }
}

# Sample Mutation: add a new visit 
#  (hint: run the onNewVisit subscription in second GraphiQL instance,
#  before running the mutation)
mutation AddVisit {
    addVisit(input:{
        petId:3,
        description:"Check teeth",
        date:"2024/03/30",
        vetId:1
    }) {
        newVisit:visit {
            id
            pet {
                id 
                name 
                birthDate
            }
        }
    }
}    

# Subscription for new visits
# When running this subscription, the operation runs until
#   you close it. While the operation is running, new visits
#   are received
subscription NewVisit {
    newVisit: onNewVisit {
        description
        treatingVet {
            id
            firstName
            lastName
        }
        pet {
            id
            name
        }
    }
}
        `}
        />
      </>
    );
  }

  return <LoginForm onLogin={(token) => setCurrentLogin(token)} />;
}
