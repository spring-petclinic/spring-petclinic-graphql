function initGraphiQL() {

  let graphiQLStarted = false;
  let token = localStorage.getItem('petclinic.graphiql.token');
  let currentUsername = localStorage.getItem('petclinic.graphiql.username');

  const graphiQLContainer = document.getElementById("graphiql");
  const usernameField = document.getElementById("petclinicUsername");
  const passwordField = document.getElementById("petclinicPassword");
  const loginButton = document.getElementById("petclinicLogin")
  const loginFeedback = document.getElementById("loginFeedback");
  const loginForm = document.getElementById("loginForm");

  const currentUserPanel = document.getElementById("currentUserPanel");
  const currentUserNameElement = document.getElementById("currentUserName");
  const logoutButton = document.getElementById("logoutButton");
  const petclinicTokenElement = document.getElementById("petclinicToken");

  function displayCurrentUser() {
    loginForm.style.display = "none";
    currentUserNameElement.innerHTML = `Logged in as <b>${currentUsername}</b>`;
    petclinicTokenElement.innerHTML = token;
    currentUserPanel.style.display = "block";
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
  logoutButton.addEventListener("click", logout)

  if (token) {
    startGraphiQL();
    displayCurrentUser();
  }

  if (currentUsername) {
    usernameField.value = currentUsername;
  }

  function logout() {
    ReactDOM.unmountComponentAtNode(graphiQLContainer);
    graphiQLStarted = false;

    token = null;
    localStorage.removeItem('petclinic.graphiql.token');
    passwordField.value = "";
    loginForm.style.display = "block";
    currentUserPanel.style.display = "none";
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
      const wsHost = window.location.href
        .replace("https", "wss")
        .replace("http", "ws");

      const url = `${wsHost}graphql?token=${token}`

      const wsClient = graphqlWs.createClient({
        url
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
      graphiQLContainer,
    );

    graphiQLStarted = true;
  }
}

initGraphiQL();



