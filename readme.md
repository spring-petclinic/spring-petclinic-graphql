# EXPERIMENTAL version of Spring PetClinic Sample Application using spring-graphql

This PetClinic version uses the new [spring-graphql](https://github.com/spring-projects/spring-graphql) project, that has been [introduced](https://spring.io/blog/2021/07/06/hello-spring-graphql) in july 2021.

It implements a [GraphQL API](http://graphql.org/) for the PetClinic and
provides an example Frontend for the API.

## Features

As spring-graphql is still experimental, this PetClinic is experimental and in-progress too.

Some features that are built in:

* GraphQL datafetchers for Queries and Mutations
* Subscriptions via Websockets (see `VisitSubscriptionWiring`)  
* Own scalar types (See `DateScalarWiring`)
* GraphQL Interfaces (GraphQL Type `Person`) and Unions (GraphQL Type `AddVetPayload`), see class `AddVetWiring`
* Security: the `/graphql` http and WebSocket endpoints are secured and can only be accessed using a JWT token. More fine grained security is implemented using `@PreAuthorize` (see `VetService`) 
* Pagination and Sorting of results: implemented with `spring-data`, see `OwnerQueryWiring`

## Running the Server

The server is implemented in the `backend` folder and can be started either from your IDE (`org.springframework.samples.petclinic.PetClinicApplication`) or
using maven from the root folder of the repository:

```
./mvnw spring-boot:run -pl backend
```

Note: the server runs on port **9977**, so make sure, this port is available.

(The server uses an in-memory database, so no external DB is needed)

## Accessing the GraphQL API

You can access the GraphQL API via the included customized version of GraphiQL.

The included GraphiQL adds support for login to the original GraphiQL.

You can use the following users for login:

* **joe/joe**: Regular user
* **susi/susi**: has Manager Role and is allowed to execute the `createVet` Mutation

After starting the server, GraphiQL runs on [http://localhost:9977](http://localhost:9977)

**Note**: The WebSocket/Subscription support in GraphiQL is far from being robust. Use with care!

![SpringBoot PetClinic, GraphiQL](graphiql.png)


## Running the frontend

While you can access the whole GraphQL API from GraphiQL this demo application also
contains a modified version of the classic PetClinic UI. Compared to the original
client this client is built as a Single-Page-Application using **React** and **Apollo GraphQL**
and has slightly different features to make it a more realistic use-case for GraphQL.

You can install and start the frontend by using npm:

```
cd ./frontend

npm install

npm start
```

The running frontend can be accessed on [http://localhost:3000](http://localhost:3000).

For valid users to login, see list above.

![SpringBoot PetClinic, React Frontend](petclinic-ui.png)



# Contributing

If you like to help and contribute you're more than welcome! Please open [an issue](https://github.com/spring-petclinic/spring-petclinic-graphql/issues) or a [Pull Request](https://github.com/spring-petclinic/spring-petclinic-graphql/pulls)
 
# Contact

You can find [me](https://nilshartmann.net) on [Twitter](https://twitter.com/nilshartmann).
