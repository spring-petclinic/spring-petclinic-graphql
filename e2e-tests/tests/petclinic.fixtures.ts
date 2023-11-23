import { test as base } from "@playwright/test";

import { expect, type Locator, type Page } from "@playwright/test";

class LoginPage {
  constructor(readonly page: Page) {}

  async login(username: string, password: string, expectedAfterLoginHeading: string | RegExp = "Welcome to PetClinic!") {
    await expect(this.page.getByRole("heading", { name: "Login to PetClinic" })).toBeVisible();

    await this.page.getByRole("textbox", { name: /username/i }).fill(username);
    await this.page.getByRole("textbox", { name: /password/i }).fill(password);
    await this.page.getByRole("button", { name: /login/i }).click();

    await expect(this.page.getByRole("heading", { name: expectedAfterLoginHeading })).toHaveCount(1);
  }
}

export class TableModel {
  readonly rows: Locator;

  constructor(readonly page: Page, readonly tableLocator: Locator) {
    this.rows = tableLocator.locator("tbody tr");
  }

  async expectTableRowContent(rowIx: number, ...cellContents: Array<string | RegExp>) {
    const row = this.tableLocator.locator("tbody tr").nth(rowIx);
    await expect(row).toBeVisible();

    for (const [ix, content] of cellContents.entries()) {
      await expect(row.locator("td").nth(ix)).toHaveText(content);
    }
  }
}

type TableFactoryFunction = (tableLocator: Locator) => TableModel;

export const petclinicTest = base.extend<{
  loginPage: LoginPage;
  tableModel: TableFactoryFunction;
}>({
  loginPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await use(loginPage);
  },

  tableModel: async ({ page }, use) => {
    const table: TableFactoryFunction = (tableLocator) => new TableModel(page, tableLocator);
    await use(table);
  },
});
