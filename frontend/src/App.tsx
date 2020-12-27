/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import Heading from "./components/Heading";
import Input from "./components/Input";
import PageLayout from "./components/PageLayout";
import Section from "./components/Section";
import Table from "./components/Table";

function Form() {
  return (
    <Section>
      <Heading>Add Owner</Heading>
      <Input />
    </Section>
  );
}

function ExamplePage() {
  return (
    <PageLayout>
      <Table />
      <Form />
    </PageLayout>
  );
}

function App() {
  return <ExamplePage />;
  // return (
  //   <div className="py-8 px-8 max-w-sm mx-auto bg-white rounded-xl shadow-md space-y-2 sm:py-4 sm:flex sm:items-center sm:space-y-0 sm:space-x-6">
  //     <div className="text-center space-y-2 sm:text-left ">
  //       <div className="space-y-0.5">
  //         <p className="text-lg text-black font-semibold font-sans-va">
  //           Erin Lindford (VA)
  //         </p>
  //         <p className="text-gre font-sans-mo">Product Engineer</p>
  //       </div>
  //       <button className="text-pink">Message</button>
  //     </div>
  //   </div>
  // );
}

export default App;
