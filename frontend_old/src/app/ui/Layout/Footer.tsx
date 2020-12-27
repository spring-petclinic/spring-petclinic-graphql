import * as React from "react";

const imgStyle = {
  maxHeight: "42px",
  width: "42px",
  marginLeft: "20px",
  marginRight: "20px"
};

const ToolLogo = ({ href, logoName, alt }: { href: string; logoName: string; alt: string }) => {
  return (
    <a href={href} target="_blank">
      <img style={imgStyle} src={`/images/${logoName}`} alt={alt} />
    </a>
  );
};

// due to bug in prettier, the // are interpreted as comments in JSX, so
// declare the URLs here as constant
const githubUrl = "https://github.com/spring-petclinic/spring-petclinic-graphql";

const Footer = () =>
  <div className="row">
    <div style={{ paddingTop: "10px", borderTop: "2px solid #6db33f" }} className="col-12 text-center">
      Spring PetClinic React/GraphQL Example
    </div>
    <div
      className="col-12"
      style={{
        marginTop: "10px",
        display: "flex",
        justifyContent: "center"
      }}
    >
      <ToolLogo href="https://projects.spring.io/spring-boot/" logoName="spring-boot-logo.svg" alt="Powered by Spring Boot" />
      <ToolLogo href="http://graphql.org/" logoName="graphql-logo.svg" alt="Powered by GraphQL" />
      <ToolLogo href="https://facebook.github.io/react/" logoName="react-logo.svg" alt="Powered by React" />
      <ToolLogo href="http://dev.apollodata.com/react/" logoName="apollo-logo.svg" alt="Powered by React Apollo" />
    </div>
    <div style={{ marginTop: "10px" }} className="col-12 text-center">
      <a href={githubUrl} target="_blank">
        Source: {githubUrl}
      </a>
    </div>
    <div style={{ marginTop: "5px" }} className="col-12 text-center">
      <a href="https://twitter.com/nilshartmann" target="_blank">
        <img style={imgStyle} src="/images/Twitter_Logo_Blue.png" />
      </a>
    </div>
  </div>;

export default Footer;
