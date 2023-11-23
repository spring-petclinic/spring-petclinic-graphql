import { expect } from "@playwright/test";
import { petclinicTest } from "./petclinic.fixtures";

petclinicTest("vet list works", async ({ page, loginPage, tableModel }) => {
  await page.goto("/");
  await loginPage.login("susi", "susi");
  await page.getByRole("link", { name: /VETERINARIANS/i }).click();

  const vetTable = tableModel(page.getByRole("table", { name: /All Veterinarians/i }));
  await expect(vetTable.tableLocator).toBeVisible();

  // just a random sample, to make sure table displays anything
  //  and graphql request was successful
  await vetTable.expectTableRowContent(0, "Carter, James");
  await vetTable.expectTableRowContent(1, "Douglas, Linda", "dentistry, surgery");
  await vetTable.expectTableRowContent(9, "Tanaka, Akira");
});

petclinicTest("adding vet works", async ({ page, loginPage, tableModel }) => {
  await page.goto("/vets");
  await loginPage.login("susi", "susi", "Manage Veterinarians");
  const vetTable = tableModel(page.getByRole("table", { name: /All Veterinarians/i }));
  await vetTable.expectTableRowContent(0, "Carter, James");
  const oldVetCount = await vetTable.rows.count();

  // generate a new last name that is for sure at the end of the list of vets
  //  (for the case this tests runs more than one time with the same database)
  const newLastName = `XXXX-${Date.now()}`;

  await page.getByRole("button", { name: /add veterinary/i }).click();
  await page.getByLabel(/first name/i).fill("Miller");
  await page.getByLabel(/last name/i).fill(newLastName);

  await page.getByLabel(/Specialties/i).selectOption([
    {
      label: "surgery",
    },
    {
      label: "dentistry",
    },
  ]);

  await page.getByRole("button", { name: /save/i }).click();
  await expect(vetTable.tableLocator).toBeVisible();
  await vetTable.expectTableRowContent(oldVetCount, `${newLastName}, Miller`, "dentistry, surgery");
});

petclinicTest("adding vet as user is forbidden", async ({ page, loginPage, tableModel }) => {
  await page.goto("/vets");
  await loginPage.login("joe", "joe", "Manage Veterinarians");
  const vetTable = tableModel(page.getByRole("table", { name: /All Veterinarians/i }));

  await page.getByRole("button", { name: /add veterinary/i }).click();
  await page.getByLabel(/first name/i).fill("No");
  await page.getByLabel(/last name/i).fill("Way");

  await page.getByLabel(/Specialties/i).selectOption([
    {
      label: "surgery",
    },
  ]);

  await page.getByRole("button", { name: /save/i }).click();
  await expect(page.getByText(/forbidden/i)).toBeVisible();
  await expect(vetTable.tableLocator).toHaveCount(0);
});
