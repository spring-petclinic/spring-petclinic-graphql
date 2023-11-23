const backendHost = "";

export const graphqlApiUrl = `${backendHost}/graphql`;
export const loginApiUrl = `${backendHost}/api/login`;
export const pingApiUrl = `${backendHost}/ping`;

function buildWsApiUrl() {
  if (backendHost === "") {
    const url = new URL(window.location.href);
    return `${url.protocol}//${url.host}/graphqlws`;
  } else {
    return `${backendHost}/graphqlws`;
  }
}

export const graphqlWsApiUrl = buildWsApiUrl()
  .replace("https", "wss")
  .replace("http", "ws");

console.log("USING GRAPHQL API URL", graphqlApiUrl);
console.log("USING GRAPHQL SUBSCRIPTION URL", graphqlWsApiUrl);
console.log("USING LOGIN API URL", loginApiUrl);
