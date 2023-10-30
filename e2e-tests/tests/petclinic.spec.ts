import { expect } from "@playwright/test";
import { petclinicTest } from "./petclinic.fixtures";

petclinicTest("owner search works", async ({ page, loginPage }) => {
  await page.goto("/");

  await loginPage.login("susi", "susi");

  await page.getByRole("link", { name: "Owners" }).click();

  await expect(
    page.getByRole("heading", { name: /Search owner/i })
  ).toBeVisible();

  await page.getByRole("textbox", { name: /last name/i }).fill("es");
  await page.getByRole("button", { name: /find/i }).click();
  await expect(page.getByText(/2 owners found/i)).toBeVisible();
  await expect(page.locator("table tbody tr")).toHaveCount(2);
  await expect(
    page.locator("table tbody tr").nth(0).locator("td").nth(0)
  ).toHaveText("Escobito");
  await expect(
    page.locator("table tbody tr").nth(1).locator("td").nth(0)
  ).toHaveText("Estaban");
  await expect(page.getByRole("button", { name: /prev/i })).toBeDisabled();
  await expect(page.getByRole("button", { name: /1/i })).toBeDisabled();
  await expect(page.getByRole("button", { name: /next/i })).toBeDisabled();

  await page.getByRole("textbox", { name: /last name/i }).fill("");
  await page.getByRole("button", { name: /find/i }).click();
  await expect(page.getByText(/10 owners found/i)).toBeVisible();
});

petclinicTest("owner detail works", async ({ page, loginPage, tableModel }) => {
  await page.goto("/owners/6"); // "Jean Coleman"
  await loginPage.login("susi", "susi", /Owners - Jean Coleman/);

  const contactTable = tableModel(
    page.getByRole("region", { name: /contact data/i }).locator("table")
  );
  await contactTable.expectTableRowContent(0, /name/i, /Jean Coleman/i);
  await contactTable.expectTableRowContent(1, /Address/i, /105 N. Lake St./i);
  await contactTable.expectTableRowContent(2, /city/i, /Monona/i);
  await contactTable.expectTableRowContent(3, /Telephone/i, /6085552654/i);

  const maxVisitsTable = tableModel(
    page.getByRole("region", { name: /visits of max/i }).locator("table")
  );
  await expect(maxVisitsTable.tableLocator).toBeVisible();

  await maxVisitsTable.expectTableRowContent(
    0,
    "2013/01/02",
    "",
    /rabies shot/
  );
  await maxVisitsTable.expectTableRowContent(
    1,
    "2013/01/03",
    "Rafael Ortega",
    /neutered/
  );
});
