import { gql, useApolloClient } from "@apollo/client";
import Button from "components/Button";
import ButtonBar from "components/ButtonBar";
import Heading from "components/Heading";
import Input from "components/Input";
import Label from "components/Label";
import PageLayout from "components/PageLayout";
import Section from "components/Section";
import * as React from "react";
import { useForm } from "react-hook-form";

type LoginFormData = { username: string; password: string };
type LoginRequestState = { running?: boolean; error?: string };

const MeQuery = gql`
  query {
    me {
      username
      fullname
    }
  }
`;

export default function LoginPage() {
  const apolloClient = useApolloClient();
  const { register, handleSubmit, watch, errors } = useForm<LoginFormData>({});
  const [
    loginRequestState,
    setLoginRequestState,
  ] = React.useState<LoginRequestState>({ running: false });

  console.log("errors", errors);

  async function handleLogin({ username, password }: LoginFormData) {
    setLoginRequestState({
      running: true,
    });

    try {
      const response = await fetch("http://localhost:9977/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      if (response.status === 401) {
        throw new Error("Could not login. Please verify username/password.");
      }
      if (!response.ok) {
        throw new Error("Could not login");
      }
      const result = await response.json();
      if (!result.token) {
        throw new Error("Could not login. Please verify username/password.");
      }

      console.log("TOKEN RECEIVED", result.token);
      localStorage.setItem("petclinic.token", result.token);

      const r = await apolloClient.query({ query: MeQuery });
      console.log("GQL R", r);
    } catch (err) {
      console.error("LOGIN FAILED ================ >>>>>>>>>>>>>>>>> ", err);
      setLoginRequestState({ error: err.message });
    }
  }

  return (
    <PageLayout title="Welcome to PetClinic!" narrow>
      <Section>
        <Heading>Login</Heading>
        <Input
          label="Username"
          name="username"
          ref={register({ required: true })}
          error={errors.username && "Please fill in username"}
          disabled={loginRequestState.running}
        />
        <Input
          label="Password"
          name="password"
          ref={register({ required: true })}
          type="password"
          error={errors.password && "Please fill in password"}
          disabled={loginRequestState.running}
        />
        <ButtonBar>
          <Button
            disabled={loginRequestState.running}
            onClick={handleSubmit(handleLogin)}
          >
            Login
          </Button>
        </ButtonBar>
        {loginRequestState.error && <Label>{loginRequestState.error}</Label>}
      </Section>
    </PageLayout>
  );
}
