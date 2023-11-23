# Customized GraphiQL for Spring PetClinic

This module contains a customized build of the GraphiQL ui.
It shows how you can create your [own, customized GraphiQL](https://docs.spring.io/spring-graphql/reference/graphiql.html#graphiql.custombuild) for your backend that can be integrated in your Spring backend application.

Other than the original GraphiQL it has a login screen because the PetClinic GraphQL is not public, and every
request needs a JWT to get access to it.

## Tech stack

This module uses [Vite](https://vitejs.dev/) with the `react-ts` template. GraphiQL itself is added to it
as a [npm module](https://github.com/graphql/graphiql/tree/main/packages/graphiql#using-as-package).

## Install the packages

Before you run or build the module, you have to install the node packages:

```bash

pnpm install

```

## Use it locally

You can develop, run and test GraphiQL locally without embedding it into the Spring PetClinic GraphQL backend.

To do so, use the Vite command:

```
pnpm dev
```

This runs a development server that will connect itself against the running Spring PetClinic Backend. It assumes your backend runs on `http://localhost:9977` (see `urls.ts` and `vite.config.ts` for proxy settings).

If you make changes to the code in this module and save your changes, they're immediately picked up from the dev server and should be visible without the need of reloading the GraphiQL page.

## Integration in the Backend

When you open the backend on `http://localhost:9977`, there runs also the customized GraphiQL instance, but now served from spring boot.
You can update the included version of `petclinic-graphiql` in the `backend` project by running the following steps:

- build `petclinic-graphiql` by running `pnpm build`
- copy the files from `petclinic-graphiql/dist` to `backend/src/main/resources/ui/graphiql`
- Re-build the backend project and re-start it
- Opening `http://localhost:9977` should now run your GraphiQL build

> When you're working on a bash or zsh, the process can be simplified to:
>
> ```bash
>   pnpm build
>   pnpm copy-to-backend
> ```
