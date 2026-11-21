import { expect, Page } from "@playwright/test"
import { SecureVault } from "./secureVault"

export class Homepage {
  private constructor(private page: Page) {}

  // Opens the site and gets past the cookie banner and the privacy notice
  static async visit(page: Page) {
    await page.goto("index.html")
    await expect(page).toHaveTitle(/Acme Cloud – Simple and secure file sharing/i)
    const homepage = new Homepage(page)
    await homepage.acceptCookies()
    await homepage.dismissPrivacyNotice()
    return homepage
  }

  async exploreSecureVault() {
    const nav = this.page.getByRole("navigation")
    await nav.getByRole("button", { name: "Solutions" }).hover()
    await nav.getByRole("link", { name: "SecureVault" }).click()
    await expect(this.page).toHaveTitle("SecureVault")
    return new SecureVault(this.page)
  }

  private async acceptCookies() {
    await this.page.getByRole("button", { name: "Accept" }).click()
  }

  private async dismissPrivacyNotice() {
    const notice = this.page.getByTitle("Popup CTA").contentFrame()
    await notice.getByRole("button", { name: "Got it" }).click()
  }
}
