module.exports = {
  client: {
    //   it is important to NOT include generated/*.ts files
    //   otherwise queries will be found twice, once in .graphql and once in the
    //   generated types.ts file
    //   So include all .ts/.graphql-files here BUT NOT those in "src/generated"
    includes: ["src/!(generated)/**/!(*.d).{ts,tsx,graphql}"],
    service: {
      name: "PetClinic GraphQL Backend",
      localSchemaFile: "../backend/src/main/resources/graphql/petclinic.graphqls",
    },
  },
};
