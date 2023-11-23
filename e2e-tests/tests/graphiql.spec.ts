import os from "os";
import { test, expect } from "@playwright/test";

const graphiqlUrl = process.env.PW_GRAPHIQL_URL || "http://localhost:9977";

const platform = os.platform();

// https://github.com/microsoft/playwright/issues/16459#issuecomment-1242423005
const controlKey = platform === "darwin" ? "Meta" : "Control";
console.log("platform", platform, "using controlkey", controlKey);

test(`customized graphiql at '${graphiqlUrl}' works`, async ({ page }) => {
  await page.goto(graphiqlUrl);

  await expect(page).toHaveTitle(/GraphiQL :: Spring PetClinic/i);

  await page.getByRole("textbox", { name: /username/i }).fill("susi");
  await page.getByRole("textbox", { name: /password/i }).fill("susi");
  await page.getByRole("button", { name: /login/i }).click();
  await page.getByRole("button", { name: /Prettify query/i }).click();

  await expect(page.getByText(/query me/i)).toHaveCount(1);
  await expect(page.getByText(/query twoowners/i)).toHaveCount(1);

  // Clear editor
  // await page.getByLabel(/query editor/i).click();
  await page.getByLabel("Query Editor").getByRole("textbox").fill(`
    query MyUsername { 
      me {
        username
      }
    } 
  `);

  await page.getByRole("button", { name: /headers/i }).click();
  await page.getByRole("button", { name: /execute query/i }).click();

  await expect(page.getByRole("menuitem", { name: "MyUsername" })).toBeVisible();
  await page.getByRole("menuitem", { name: "MyUsername" }).click();

  await expect(page.getByLabel("Result Window")).toHaveText(/"username": "susi"/i);
});
