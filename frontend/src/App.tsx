import LoginPage from "login/LoginPage";
import React from "react";
import { Route, Switch } from "react-router-dom";
import Heading from "./components/Heading";
import Input from "./components/Input";
import Section from "./components/Section";

// function Form() {
//   return (
//     <Section>
//       <Heading>Add Owner</Heading>
//       <Input />
//     </Section>
//   );
// }

// function ExamplePage() {
//   return (
//     <PageLayout>
//       <Table />
//       <Form />
//     </PageLayout>
//   );
// }

function App() {
  return (
    <Switch>
      <Route path="/" exact>
        <LoginPage />
      </Route>
    </Switch>
  );
}

export default App;
