import { useAuthToken } from "@/login/AuthTokenProvider.tsx";
import { useMeLazyQuery } from "@/generated/graphql-types.ts";
import LoginPage from "@/login/LoginPage.tsx";
import { AnonymousPageLayout } from "@/components/PageLayout.tsx";
import { Route, Routes } from "react-router-dom";
import WelcomePage from "@/WelcomePage.tsx";
import OwnersPage from "@/owners/OwnerSearchPage.tsx";
import OwnerPage from "@/owners/OwnerPage.tsx";
import VetsPage from "@/vets/VetsPage.tsx";
import NotFoundPage from "@/NotFoundPage.tsx";
import { useEffect } from "react";

function App() {
  const [token] = useAuthToken();
  const [queryMe, { called, loading, error }] = useMeLazyQuery();

  useEffect(() => {
    // don't try to read user data if we don't have token.
    if (token) {
      queryMe();
    }
  }, [queryMe, token]);

  if (!token || error) {
    return <LoginPage />;
  }

  if (loading || !called) {
    return <AnonymousPageLayout title="Initializing..."></AnonymousPageLayout>;
  }

  return (
    <Routes>
      <Route path="/">
        <Route index element={<WelcomePage />} />
        <Route path={"owners"}>
          <Route index element={<OwnersPage />} />
          <Route path={":ownerId"} element={<OwnerPage />} />
        </Route>
        <Route path="/vets/:vetId?" element={<VetsPage />} />
      </Route>
      <Route path={"*"} element={<NotFoundPage />} />
    </Routes>
  );
}

export default App;
