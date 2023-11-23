import { expect } from "@playwright/test";
import { petclinicTest } from "./petclinic.fixtures";
import { v4 as uuidv4 } from "uuid";

petclinicTest("owner detail works", async ({ page, loginPage, tableModel }) => {
  await page.goto("/owners/6"); // "Jean Coleman"
  await loginPage.login("susi", "susi", /Owners - Jean Coleman/);

  const contactTable = tableModel(page.getByRole("region", { name: /contact data/i }).locator("table"));
  await contactTable.expectTableRowContent(0, /name/i, /Jean Coleman/i);
  await contactTable.expectTableRowContent(1, /Address/i, /105 N. Lake St./i);
  await contactTable.expectTableRowContent(2, /city/i, /Monona/i);
  await contactTable.expectTableRowContent(3, /Telephone/i, /6085552654/i);

  const maxVisitsTable = tableModel(page.getByRole("region", { name: /visits of max/i }).locator("table"));
  await expect(maxVisitsTable.tableLocator).toBeVisible();

  await maxVisitsTable.expectTableRowContent(0, "2013/01/02", "", /rabies shot/);
  await maxVisitsTable.expectTableRowContent(1, "2013/01/03", "Rafael Ortega", /neutered/);
});

petclinicTest("add visit", async ({ context, page, loginPage, tableModel }) => {
  await page.goto("/owners/7"); // "Jeff Black"
  await loginPage.login("susi", "susi", /Owners - Jeff Black/);

  const secondPage = await context.newPage();
  await secondPage.goto("/owners/7"); // "Jeff Black"

  const luckyVisitsTable = tableModel(page.getByRole("region", { name: /visits of lucky/i }).locator("table"));
  await luckyVisitsTable.tableLocator.isVisible();

  const oldVisitCount = await luckyVisitsTable.rows.count();

  const secondLuckyVisitsTable = tableModel(secondPage.getByRole("region", { name: /visits of lucky/i }).locator("table"));
  await expect(secondLuckyVisitsTable.rows).toHaveCount(oldVisitCount);

  await page.getByRole("button", { name: /Add visit for pet Lucky/i }).click();

  const form = page.getByRole("region", { name: /Add visit for pet Lucky/i });
  await expect(form).toBeVisible();

  const description = `description-${uuidv4()}`;

  await form.getByLabel(/date/i).fill("2024-08-20");
  await form.getByLabel(/description/i).fill(description);
  await form.getByLabel(/vet/i).selectOption({
    label: "Linda Douglas",
  });
  await form.getByRole("button", { name: /save/i }).click();

  await luckyVisitsTable.expectTableRowContent(oldVisitCount, "2024/08/20", "Linda Douglas", description);

  // Check subscription: new created visit
  //  should automatically be visible in second browser tab
  await secondLuckyVisitsTable.expectTableRowContent(oldVisitCount, "2024/08/20", "Linda Douglas", description);
});
