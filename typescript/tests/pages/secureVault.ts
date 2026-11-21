import { expect, Page } from "@playwright/test"
import { Inquiry, InquiryForm } from "../components/inquiryForm"

export class SecureVault {
  constructor(private page: Page) {}

  // Opens the inquiry form, fills it in, gives consent and sends it
  async sendInquiry(inquiry: Inquiry) {
    await this.page.getByRole("link", { name: "Send inquiry" }).click()
    await new InquiryForm(this.page).submit(inquiry)
  }

  async confirmsInquiryFrom(firstName: string) {
    await expect(this.page.getByRole("status")).toHaveText(
      `Thank you, ${firstName}. We will get back to you soon.`
    )
  }
}
