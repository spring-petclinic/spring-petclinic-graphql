import { expect } from "@playwright/test";
import { petclinicTest } from "./petclinic.fixtures";

petclinicTest("owner search works", async ({ page, loginPage }) => {
  await page.goto("http://localhost:3080");

  await loginPage.login("susi", "susi");

  await page.getByRole("link", { name: "Owners" }).click();

  await expect(
    page.getByRole("heading", { name: /Search owner/i })
  ).toBeVisible();

  await page.getByRole("textbox", { name: /last name/i }).fill("es");
  await page.getByRole("button", { name: /find/i }).click();
  await expect(page.getByText(/2 owners found/i)).toBeVisible();
  await expect(page.locator("table tbody tr")).toHaveCount(2);
  await expect(page.getByRole("button", { name: /prev/i })).toBeDisabled();
  await expect(page.getByRole("button", { name: /1/i })).toBeDisabled();
  await expect(page.getByRole("button", { name: /next/i })).toBeDisabled();

  await page.getByRole("textbox", { name: /last name/i }).fill("");
  await page.getByRole("button", { name: /find/i }).click();
  await expect(page.getByText(/10 owners found/i)).toBeVisible();
});
