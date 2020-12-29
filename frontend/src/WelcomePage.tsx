import Heading from "components/Heading";
import PageLayout from "components/PageLayout";
import * as React from "react";

export default function WelcomePage() {
  const publicPath = process.env.PUBLIC_URL;
  const petImgSrc = `${publicPath}/assets/pets.png`;
  return (
    <PageLayout title="Welcome">
      <Heading>Welcome</Heading>
      <img src={petImgSrc} alt="Pets" />
    </PageLayout>
  );
}
