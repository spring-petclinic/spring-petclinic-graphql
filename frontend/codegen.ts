import type { CodegenConfig } from "@graphql-codegen/cli";

const config: CodegenConfig = {
  overwrite: true,
  schema: "../backend/src/main/resources/graphql/petclinic.graphqls",
  documents: ["src/**/*.tsx", "src/**/*.graphql"],
  ignoreNoDocuments: true,
  generates: {
    "src/generated/graphql-types.ts": {
      plugins: [
        "typescript",
        "typescript-operations",
        "typescript-react-apollo",
      ],
      config: {
        skipTypename: true,
        preResolveTypes: true,
        declarationKind: "interface",
        onlyOperationTypes: true,
      },
    },
  },
};

export default config;
