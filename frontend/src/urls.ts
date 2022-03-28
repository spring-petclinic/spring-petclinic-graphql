declare var process: any;

const backendHost = window.__petclinic__backend_host__.replace(
  "__BACKEND__",
  "http://localhost:9977"
);
export const graphqlApiUrl = `${backendHost}/graphql`;
export const loginApiUrl = `${backendHost}/login`;

console.log("USING GRAPHQL API URL", graphqlApiUrl);
console.log("USING LOGIN API URL", loginApiUrl);
