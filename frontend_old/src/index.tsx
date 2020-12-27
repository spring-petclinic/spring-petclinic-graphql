import * as React from "react";
import * as ReactDOM from "react-dom";
import { AppContainer } from "react-hot-loader";
import { ApolloProvider } from "react-apollo";

import { BrowserRouter } from "react-router-dom";

import "./app/ui/styles/less/petclinic.less";
import { createGraphQLClient } from "./createGraphQLClient";
import App from "./app/App";

const graphQLClient = createGraphQLClient();

const renderApp = (Component: typeof App) => {
  ReactDOM.render(
    <AppContainer>
      <ApolloProvider client={graphQLClient}>
        <BrowserRouter>
          <Component />
        </BrowserRouter>
      </ApolloProvider>
    </AppContainer>,
    document.getElementById("mount")
  );
};

renderApp(App);

if (module.hot) {
  module.hot.accept("./app/App", () => {
    // Hot Re-load should work without re-require according to Webpack 2 docs,
    // but does not
    const NextApp = require<RequireImport>("./app/App").default;
    renderApp(NextApp);
  });
}
