import * as React from "react";

const githubUrl = "https://github.com/spring-petclinic/spring-petclinic-graphql";

type LinkList = [[string, string]];

const serverLinks: LinkList = [
  ["GraphQL Java Tools", "https://github.com/graphql-java/graphql-java-tools"],
  ["GraphQL Spring Boot", "https://github.com/graphql-java/graphql-spring-boot"],
  ["Spring Boot", "https://projects.spring.io/spring-boot/"]
];

const clientLinks: LinkList = [
  ["React Apollo", "https://github.com/apollographql/react-apollo"],
  ["React", "https://facebook.github.io/react/"],
  ["TypeScript", "http://www.typescriptlang.org/"]
];

const LinkList = ({ links }: { links: LinkList }) =>
  <ul>
    {links.map(link =>
      <li key={link[0]}>
        <a href={link[1]}>
          {link[0]}
        </a>
      </li>
    )}
  </ul>;

const WelcomePage = () =>
  <span>
    <div className="row">
      <div className="col-md-3">
        <img className="img-responsive" src="/images/pets.png" />
      </div>
      <div className="col-md-9">
        <h1 style={{ fontSize: "32px", fontWeight: "bold", lineHeight: "1.5" }}>Welcome</h1>
        <h2>Spring PetClinic - React/GraphQL Edition</h2>
        <h3>
          <a href={githubUrl}>
            {githubUrl}
          </a>
        </h3>
        <h3>Server built with:</h3>
        <LinkList links={serverLinks} />
        <h3>Client built with:</h3>
        <LinkList links={clientLinks} />
      </div>
    </div>
  </span>;

export default WelcomePage;
