import Heading from "components/Heading";
import PageLayout from "components/PageLayout";
import * as React from "react";

export default function NotFoundPage() {
  return (
    <PageLayout title="Not Found">
      <Heading>Sorry, the requested page could not be found</Heading>
    </PageLayout>
  );
}
