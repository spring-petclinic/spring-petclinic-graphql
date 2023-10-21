import Heading from "./components/Heading";
import PageLayout from "./components/PageLayout";
import petsImage from "@/assets/pets.png";

export default function WelcomePage() {
  return (
    <PageLayout title="Welcome">
      <Heading>Welcome</Heading>
      <img src={petsImage} alt="Pets" />
    </PageLayout>
  );
}
