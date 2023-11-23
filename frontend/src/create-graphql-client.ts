import {
  ApolloClient,
  createHttpLink,
  InMemoryCache,
  split,
} from "@apollo/client";
import { graphqlApiUrl, graphqlWsApiUrl } from "@/urls.ts";
import { setContext } from "@apollo/client/link/context";
import { createClient } from "graphql-ws";
import { GraphQLWsLink } from "@apollo/client/link/subscriptions";
import {
  getMainDefinition,
  relayStylePagination,
} from "@apollo/client/utilities";

// noinspection JSUnusedLocalSymbols
// const token =
//   "eyJraWQiOiJmYTg5ZmU1OC02ZDk2LTQxNzYtODVmYy1jZmE2ODAzY2IzOWQiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoic3VzaSIsImV4cCI6MjAxNTc0NjkwNywiaWF0IjoxNzAwMzg2OTA3LCJzY29wZSI6Ik1BTkFHRVIifQ.ZD_08AtPvJvDfraULVZgH07cgG0OqYRHC8Ie6aWLb802rnpgKjv83l6UE7uSBpMgmqHr2BFGuSwYI5L9wjXuImygF_f4U2sote7zv0M5I2Ou_9CgXaLbAN-NQP19aCaHcRLEQfBH11vGoBsnBtZ_qrmUBdaj-RCrqpuzmmlJAlDYmwhE5hFWt9qSU-SadudBV9rlIikWFEAbUAjJk-m2vdRu_dSVnCQb8DQLnniuSSR11szkmi-BD0BoL0nJQ5PqQ8G2JQUxwQDq2-VPmMPNOX_pbFEPuvKhlgUClqPNW1QWM1A3Vw74KPgGw-9-uUuIia6enxzpOVz69vhKHIxrOr7qebzQ_XwEt46RkKXeA1Vzb-Xemy3Q5ESESN-J1tnwPvJJKwL8UgHUvghLk-ya8uiY7bfORAJZ8nN7riE6eO1UYelpuK6Euk3eTP94De9MjnnV7yvKmay65PIDNO7jqA8UOwJ74LuQ483DJdHog3qh6NafW1IxaOEzrEbSvgGCv_VoypHs5vpITD3sNky1HikNqzVtiptoQaX2Hi5OI-laAq8itqvpF5l5I9lbivy2rReXhj3_m_U_HVDxW8v8zD5PCYxGFEA2GOK9Qu_gVTcMgLJJE-D0e-IkvIk85nC5JimuCsmddGxcxmN_ILeRdZLyM60t7fz8-y-Yx53i5YM";

export function createGraphqlClient() {
  const httpLink = createHttpLink({
    uri: graphqlApiUrl,
  });

  const wsLink = new GraphQLWsLink(
    createClient({
      url: () => {
        const token = localStorage.getItem("petclinic.token");
        console.log("Create WS Link", token);
        return `${graphqlWsApiUrl}?access_token=${token}`;
      },
      on: {
        closed: () => console.log("WS closed"),
      },
    }),
  );

  const authLink = setContext((op, { headers }) => {
    const token = localStorage.getItem("petclinic.token");
    console.log("AUTH LINK", op.operationName);
    return {
      headers: {
        ...headers,
        authorization: token ? `Bearer ${token}` : "",
      },
    };
  });

  const splitLink = split(
    ({ query }) => {
      const definition = getMainDefinition(query);
      return (
        definition.kind === "OperationDefinition" &&
        definition.operation === "subscription"
      );
    },
    wsLink,
    httpLink,
  );

  const client = new ApolloClient({
    link: authLink.concat(splitLink),
    cache: new InMemoryCache({
      typePolicies: {
        Query: {
          fields: {
            //https://www.apollographql.com/docs/react/pagination/cursor-based/#relay-style-cursor-pagination
            owners: relayStylePagination(),
          },
        },
        User: {
          keyFields: ["username"],
        },
      },
    }),
  });

  return client;
}
