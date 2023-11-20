import { test, expect } from "@playwright/test";

const graphiqlUrl = process.env.PW_GRAPHIQL_URL || "http://localhost:9977";

test(`customized graphiql at '${graphiqlUrl}' works`, async ({ page }) => {
  await page.goto(graphiqlUrl);

  await expect(page).toHaveTitle(/GraphiQL :: Spring PetClinic/i);

  await page.getByRole("textbox", { name: /username/i }).fill("susi");
  await page.getByRole("textbox", { name: /password/i }).fill("susi");
  await page.getByRole("button", { name: /login/i }).click();

  await expect(page.getByLabel("Query Editor")).toBeVisible();

  await page.getByLabel("Query Editor").getByRole("textbox").fill(`
  {
    me {
      username
    }
  }
  `);

  await page.getByRole("button", { name: /execute query/i }).click();

  await expect(page.locator(".graphiql-response")).toHaveText(
    /"username": "susi"/i
  );
});
