import { useState } from "react";
import { loginApiUrl } from "./urls.ts";

type LoginFormProps = {
  onLogin(login: { username: string; token: string }): void;
};
export default function LoginForm({ onLogin }: LoginFormProps) {
  const [usernamePassword, setUsernamePassword] = useState<{
    username: string;
    password: string;
  }>({
    username: "",
    password: "",
  });

  function onInputChange(e: React.ChangeEvent<HTMLInputElement>) {
    setLoginMsg("");
    setUsernamePassword({
      ...usernamePassword,
      [e.target.name]: e.target.value,
    });
  }

  const [loginMsg, setLoginMsg] = useState("");

  async function login() {
    setLoginMsg("");

    const response = await fetch(loginApiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      body: JSON.stringify(usernamePassword),
    });

    if (!response.ok) {
      localStorage.removeItem("petclinic.graphiql.username");
      localStorage.removeItem("petclinic.graphiql.token");

      setLoginMsg("Login failed!");
    }

    const result = await response.json();
    const token = result.token as string;
    if (!token) {
      setLoginMsg("No token in response from server!");
      return;
    }
    localStorage.setItem(
      "petclinic.graphiql.username",
      usernamePassword.username,
    );
    localStorage.setItem("petclinic.graphiql.token", token);
    onLogin({ token, username: usernamePassword.username });
  }

  return (
    <div id={"loginPage"}>
      <h1>Login to Spring PetClinic GraphiQL</h1>
      <div id="loginForm">
        <label>
          Username:
          <input type="text" name="username" onChange={onInputChange} />
        </label>

        <label>
          Password:
          <input type="password" name="password" onChange={onInputChange} />
        </label>

        <button onClick={login}>Login</button>
        <p id="loginFeedback">{loginMsg}</p>
        <div style={{marginTop: "4rem"}}>
            <p>Choose one of the following users for login:</p>
            <table>
              <thead>
                <tr>
                  <th>Username</th>
                  <th>Password</th>
                  <th>Role</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>susi</td>
                  <td>susi</td>
                  <td>ROLE_MANAGER</td>
                </tr>
                <tr>
                  <td>joe</td>
                  <td>joe</td>
                  <td>ROLE_USER</td>
                </tr>
              </tbody>
            </table>
        </div>
      </div>
    </div>
  );
}
