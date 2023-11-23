# Custom GraphiQL build

Inside `graphiql` is a custom graphiql build that is built from `petclinic-graphiql` and then copied
into `graphiql/`.

In a real project you maybe would not add the built version to source control but to add it to `classes/...` during
the build process.

Here I added the built graphiql code to Git because I don't want to force every to setup the JavaScript-based build for the
GraphiQL frontend. If you don't care about the JavaScript-parts of this example project (frontend/graphiql) you can just
run the backend and use(the customized) GraphiQL anyway.
