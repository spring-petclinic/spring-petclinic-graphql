import Button from "components/Button";
import ButtonBar from "components/ButtonBar";
import Card from "components/Card";
import Heading from "components/Heading";
import Input from "components/Input";
import Label from "components/Label";
import { AnonymousPageLayout } from "components/PageLayout";
import Section from "components/Section";
import Table from "components/Table";
import * as React from "react";
import { useForm } from "react-hook-form";
import { loginApiUrl } from "urls";
import { useAuthToken } from "./AuthTokenProvider";

type LoginFormData = { username: string; password: string };
type LoginRequestState = { running?: boolean; error?: string };

export default function LoginPage() {
	const [, updateToken] = useAuthToken();
	const { register, handleSubmit, errors } = useForm<LoginFormData>({});
	const [
		loginRequestState,
		setLoginRequestState,
	] = React.useState<LoginRequestState>({ running: false });

	async function handleLogin({ username, password }: LoginFormData) {
		setLoginRequestState({
			running: true,
		});

		try {
			const response = await fetch(loginApiUrl, {
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
			updateToken(result.token);
		} catch (err) {
			console.error("LOGIN FAILED ================ >>>>>>>>>>>>>>>>> ", err);
			setLoginRequestState({ error: err.message });
		}
	}

	return (
		<AnonymousPageLayout title="Welcome to PetClinic!">
			<Section narrow>
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
			<Card fullWidth>
				<div className="flex flex-col w-full">
					<Heading level="2">Users</Heading>
					<p>
						Choose one of the following users for login:
					</p>
					<Table
						labels={["Username", "Password", "Role"]}
						values={[
							["susi", "susi", "ROLE_MANAGER"],
							["joe", "joe", "ROLE_USER"],
						]}
					/>
				</div>
			</Card>
		</AnonymousPageLayout>
	);
}
