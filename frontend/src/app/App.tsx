import * as React from "react";

import { Switch, Route } from "react-router-dom";

import Layout from "./ui/Layout";

import NotFoundPage from "./ui/NotFoundPage";
import WelcomePage from "./ui/WelcomePage";
import { OwnerListPage, OwnerPage, AddOwnerPage, UpdateOwnerPage } from "./domain/owner";
import { VetListPage } from "./domain/vet";
import { AddPetPage, UpdatePetPage } from "./domain/pet";
import { AddVisitPage } from "./domain/visit";
import { SpecialtiesPage } from "./domain/specialty";

const App = () =>
  <Layout>
    <Switch>
      <Route exact path="/" component={WelcomePage} />
      <Route exact path="/owners" component={OwnerListPage} />
      <Route exact path="/owners/add" component={AddOwnerPage} />
      <Route exact path="/owners/:ownerId" component={OwnerPage} />
      <Route exact path="/owners/:ownerId/pets/new" component={AddPetPage} />
      <Route exact path="/owners/:ownerId/pets/:petId/visits/new" component={AddVisitPage} />
      <Route exact path="/owners/:ownerId/pets/:petId/edit" component={UpdatePetPage} />
      <Route exact path="/owners/:ownerId/edit" component={UpdateOwnerPage} />
      <Route exact path="/vets" component={VetListPage} />
      <Route exact path="/specialties" component={SpecialtiesPage} />
      <Route component={NotFoundPage} />
    </Switch>
  </Layout>;

export default App;
