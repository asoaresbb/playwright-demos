import { expect, Page } from "@playwright/test"
import { SecureVault } from "./secureVault"

export class Homepage {
  constructor(private page: Page) {}

  static async navigate(page: Page) {
    await page.goto("index.html")
    await expect(page).toHaveTitle(/Acme Cloud – Simple and secure file sharing/i)
    return new Homepage(page)
  }

  async acceptCookies() {
    await this.page.getByRole("button", { name: "Accept" }).click()
  }

  async closePrivacyPolicy() {
    const policy = this.page.getByTitle("Popup CTA").contentFrame()
    await policy.getByRole("button", { name: "Got it" }).click()
  }

  async pickSecureVaultSolution() {
    const nav = this.page.getByRole("navigation")
    await nav.getByRole("button", { name: "Solutions" }).hover()
    await nav.getByRole("link", { name: "SecureVault" }).click()
    await expect(this.page).toHaveTitle("SecureVault")
    return new SecureVault(this.page)
  }
}
