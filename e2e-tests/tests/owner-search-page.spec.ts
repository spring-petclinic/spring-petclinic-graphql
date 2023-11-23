import { Locator, Page, expect } from "@playwright/test";
import { TableModel, petclinicTest } from "./petclinic.fixtures";

class OwnerSearchPage {
  readonly loadMoreButton: Locator;
  readonly orderAscButton: Locator;
  readonly orderDescButton: Locator;
  readonly resultTable: TableModel;

  constructor(readonly page: Page) {
    this.loadMoreButton = this.page.getByRole("button", { name: /load more/i });
    this.orderAscButton = this.page.getByRole("button", {
      name: /Order owners by lastname, ascending/i,
    });
    this.orderDescButton = this.page.getByRole("button", {
      name: /Order owners by lastname, descending/i,
    });
    this.resultTable = new TableModel(page, page.getByRole("table", { name: /Owners/i }));
  }

  heading() {
    return this.page.getByRole("heading", { name: /Search owner/i });
  }

  async find(term: string) {
    await this.page.getByRole("textbox", { name: /last name/i }).fill(term);
    await this.page.getByRole("button", { name: /find/i }).click();
  }
}

petclinicTest("owner search works", async ({ page, loginPage }) => {
  await page.goto("/");

  await loginPage.login("susi", "susi");
  await page.getByRole("link", { name: "Owners" }).click();

  const ownerSearchPage = new OwnerSearchPage(page);
  await expect(ownerSearchPage.heading()).toBeVisible();

  await ownerSearchPage.find("du");
  await expect(ownerSearchPage.orderAscButton).toBeDisabled();
  await expect(ownerSearchPage.orderDescButton).toBeEnabled();
  await expect(ownerSearchPage.loadMoreButton).toBeDisabled();
  await expect(ownerSearchPage.resultTable.rows).toHaveCount(3);

  await ownerSearchPage.resultTable.expectTableRowContent(
    0,
    "Dubois",
    "Sophie",
    "456 Rue de la Paix",
    "Paris",
    "+33 6 12 34 56 78",
    "Luna, Ollie"
  );
  await ownerSearchPage.resultTable.expectTableRowContent(1, "Dufresne", "Antoine");
  await ownerSearchPage.resultTable.expectTableRowContent(2, "Dupont", "François");

  await ownerSearchPage.orderDescButton.click();
  await expect(ownerSearchPage.orderAscButton).toBeEnabled();
  await expect(ownerSearchPage.orderDescButton).toBeDisabled();
  await ownerSearchPage.resultTable.expectTableRowContent(0, "Dupont", "François");
  await ownerSearchPage.resultTable.expectTableRowContent(1, "Dufresne", "Antoine");
  await ownerSearchPage.resultTable.expectTableRowContent(2, "Dubois", "Sophie");

  await ownerSearchPage.orderAscButton.click();
  await ownerSearchPage.find("d");
  await expect(ownerSearchPage.loadMoreButton).toBeEnabled();
  await expect(ownerSearchPage.resultTable.rows).toHaveCount(5);
  await ownerSearchPage.resultTable.expectTableRowContent(0, "da Silva");
  await ownerSearchPage.resultTable.expectTableRowContent(1, "Davis");
  await ownerSearchPage.resultTable.expectTableRowContent(2, "Davis");
  await ownerSearchPage.resultTable.expectTableRowContent(3, "Dubois");
  await ownerSearchPage.resultTable.expectTableRowContent(4, "Dufresne");

  await ownerSearchPage.loadMoreButton.click();
  await expect(ownerSearchPage.loadMoreButton).toBeDisabled();
  await expect(ownerSearchPage.resultTable.rows).toHaveCount(6);
  await ownerSearchPage.resultTable.expectTableRowContent(0, "da Silva"); // should be unchanged
  await ownerSearchPage.resultTable.expectTableRowContent(5, "Dupont"); // new fetched item
});
