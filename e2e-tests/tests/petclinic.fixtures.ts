import { test as base } from "@playwright/test";

import { expect, type Locator, type Page } from "@playwright/test";

class LoginPage {
  constructor(readonly page: Page) {}

  async login(username: string, password: string) {
    await expect(
      this.page.getByRole("heading", { name: "Login to PetClinic" })
    ).toBeVisible();

    await this.page.getByRole("textbox", { name: /username/i }).fill(username);
    await this.page.getByRole("textbox", { name: /password/i }).fill(password);
    await this.page.getByRole("button", { name: /login/i }).click();

    await expect(
      this.page.getByRole("heading", { name: "Welcome to PetClinic!" })
    ).toBeVisible();
  }
}

export const petclinicTest = base.extend<{ loginPage: LoginPage }>({
  loginPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await use(loginPage);
  },
});
