const backendHost = window.__petclinic__backend_host__.replace(
  "__BACKEND__",
  "http://localhost:9977",
);

export const graphqlApiUrl = `${backendHost}/graphql`;
export const loginApiUrl = `${backendHost}/login`;
export const pingApiUrl = `${backendHost}/ping`;

function buildWsApiUrl() {
  if (backendHost === "") {
    const url = new URL(window.location.href);
    return `${url.protocol}//${url.host}/graphql`;
  } else {
    return `${backendHost}/graphql`;
  }
}

export const graphqlWsApiUrl = buildWsApiUrl()
  .replace("https", "wss")
  .replace("http", "ws");

console.log("USING GRAPHQL API URL", graphqlApiUrl);
console.log("USING GRAPHQL SUBSCRIPTION URL", graphqlWsApiUrl);
console.log("USING LOGIN API URL", loginApiUrl);
