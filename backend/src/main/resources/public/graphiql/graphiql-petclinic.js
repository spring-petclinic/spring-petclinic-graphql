function initGraphiQL() {

  let graphiQLStarted = false;
  let token = localStorage.getItem('petclinic.graphiql.token');
  let currentUsername = localStorage.getItem('petclinic.graphiql.username');

  const usernameField = document.getElementById("petclinicUsername");
  const passwordField = document.getElementById("petclinicPassword");
  const loginButton = document.getElementById("petclinicLogin")
  const loginFeedback = document.getElementById("loginFeedback");

  function displayCurrentUser() {
    loginFeedback.innerHTML = `(Logged in as <b>${currentUsername}</b>)<code id="petclinicToken">${token}<code>`;
  }

  function onEnter(event) {
    if (event.keyCode === 13) {
      login();
      return false;
    }
  }

  usernameField.addEventListener("keypress", onEnter);
  passwordField.addEventListener("keypress", onEnter);
  loginButton.addEventListener("click", login);

  if (token) {
    startGraphiQL();
    displayCurrentUser();
  }

  if (currentUsername) {
    usernameField.value = currentUsername;
  }


  async function login() {
    loginFeedback.innerHTML = "";
    const username = usernameField.value;
    const password = passwordField.value;

    const response = await fetch("/login", {
      method:  "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8"
      },
      body:    JSON.stringify({ username, password }),
    });

    if (response.ok) {
      const result = await response.json();
      currentUsername = username;
      token = result.token;
      localStorage.setItem('petclinic.graphiql.username', username);
      localStorage.setItem('petclinic.graphiql.token', token);
      displayCurrentUser();

      startGraphiQL();
      return;
    }

    localStorage.removeItem('petclinic.graphiql.username');
    localStorage.removeItem('petclinic.graphiql.token');
    token = null;
    currentUsername = null;

    loginFeedback.innerHTML = `<b>Login failed!<b>`;
  }

  function startGraphiQL() {
    if (graphiQLStarted) {
      return;
    }

    function graphQLFetcher(graphQLParams) {
      console.log("graphQLParams", graphQLParams);
      console.log("TOKEN", token);
      const headers = {
        Accept:         'application/json',
        'Content-Type': 'application/json',
        Authorization:  token ? `Bearer ${token}` : undefined
      }
      return fetch(
        '/graphql',
        {
          method:      'post',
          headers,
          body:        JSON.stringify(graphQLParams),
          credentials: 'omit',
        },
      ).then(function(response) {
        return response.json().catch(function() {
          return response.text();
        });
      });
    }

    function buildFetcher() {
      const wsClient = graphqlWs.createClient({
        url: `ws://localhost:9977/graphql?token=${token}`
      });

      return window.GraphiQLSubscriptionsFetcher.graphQLFetcher(wsClient, graphQLFetcher);
    }

    ReactDOM.render(
      React.createElement(GraphiQL, {
        fetcher:                    buildFetcher(),
        defaultQuery:               "",
        defaultVariableEditorOpen:  true,
        defaultSecondaryEditorOpen: true,
        headerEditorEnabled:        true,
      }),
      document.getElementById('graphiql'),
    );

    graphiQLStarted = true;
  }
}

initGraphiQL();



