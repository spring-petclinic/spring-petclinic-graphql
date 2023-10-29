import { test, expect } from "@playwright/test";

test("petclinic works", async ({ page }) => {
  await page.goto("http://localhost:3080");

  await expect(
    page.getByRole("heading", { name: "Login to PetClinic" })
  ).toBeVisible();

  await page.getByRole("textbox", { name: /username/i }).fill("susi");
  await page.getByRole("textbox", { name: /password/i }).fill("susi");
  await page.getByRole("button", { name: /login/i }).click();

  await expect(
    page.getByRole("heading", { name: "Welcome to PetClinic!" })
  ).toBeVisible();

  await page.getByRole("link", { name: "Owners" }).click();

  await expect(
    page.getByRole("heading", { name: /Search owner/i })
  ).toBeVisible();

  await page.getByRole("textbox", { name: /last name/i }).fill("Da");
  await page.getByRole("button", { name: /find/i }).click();
  await expect(page.getByText(/2 owners found/i)).toBeVisible();
  await expect(page.locator("table tbody tr")).toHaveCount(2);
});
