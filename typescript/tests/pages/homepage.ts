import { expect, Page } from "@playwright/test"
import { SecureVault } from "./secureVault"
import { step } from "../testStep"

export class Homepage {
  constructor(private page: Page) {}

  @step
  static async navigate(page: Page) {
    await page.goto("index.html")
    await expect(page).toHaveTitle(/Acme Cloud – Simple and secure file sharing/i)
    return new Homepage(page)
  }

  @step
  async acceptCookies() {
    await this.page.getByRole("button", { name: "Accept" }).click()
  }

  @step
  async closePrivacyPolicy() {
    const policyIframe = this.page.frameLocator("iframe[title='Popup CTA']")
    await policyIframe.locator("css=.button-container").click()
  }

  @step
  async pickSecureVaultSolution() {
    const nav = this.page.getByRole("navigation")
    await nav.getByRole("button", { name: "Solutions" }).hover()
    await nav.getByRole("link", { name: "SecureVault" }).click()
    await expect(this.page).toHaveTitle("SecureVault")
    return new SecureVault(this.page)
  }
}
