import * as React from "react";
import * as ReactDOM from "react-dom/client";
import "./index.css";
import {
  ApolloClient,
  ApolloProvider,
  createHttpLink,
  InMemoryCache,
} from "@apollo/client";
import { setContext } from "@apollo/client/link/context";
import { AuthTokenProvider } from "./login/AuthTokenProvider";
import { graphqlApiUrl } from "./urls";
import { BrowserRouter } from "react-router-dom";
import App from "./App.tsx";

const httpLink = createHttpLink({
  uri: graphqlApiUrl,
});

const authLink = setContext((_, { headers }) => {
  const token = localStorage.getItem("petclinic.token");
  return {
    headers: {
      ...headers,
      authorization: token ? `Bearer ${token}` : "",
    },
  };
});

const client = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache({
    typePolicies: {
      User: {
        keyFields: ["username"],
      },
    },
  }),
});

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ApolloProvider client={client}>
      <BrowserRouter>
        <AuthTokenProvider>
          <App />
        </AuthTokenProvider>
      </BrowserRouter>
    </ApolloProvider>
  </React.StrictMode>,
);
