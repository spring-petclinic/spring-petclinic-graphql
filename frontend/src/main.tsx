import * as ReactDOM from "react-dom/client";
import "./index.css";
import { ApolloProvider } from "@apollo/client";
import { AuthTokenProvider } from "./login/AuthTokenProvider";
import { BrowserRouter } from "react-router-dom";
import App from "./App.tsx";
import { createGraphqlClient } from "@/create-graphql-client.ts";

const client = createGraphqlClient();

ReactDOM.createRoot(document.getElementById("root")!).render(
  <ApolloProvider client={client}>
    <BrowserRouter>
      <AuthTokenProvider>
        <App />
      </AuthTokenProvider>
    </BrowserRouter>
  </ApolloProvider>,
);
