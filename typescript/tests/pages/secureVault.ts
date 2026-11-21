import { expect, Page } from "@playwright/test"
import { InquiryForm } from "../components/inquiryForm.ts"

export class SecureVault {
  inquiryForm: InquiryForm

  constructor(private page: Page) {
    this.inquiryForm = new InquiryForm(this.page)
  }

  async goToInquiryForm() {
    await this.page.getByRole("link", { name: "Send inquiry" }).click()
  }

  async fillInquiryForm(inquiryData: {
    firstName: string
    lastName: string
    email: string
    phoneNumber: string
    message: string
  }) {
    await this.inquiryForm.fillInquiryForm(inquiryData)
  }

  async expectConfirmation(firstName: string) {
    await expect(this.page.getByRole("status")).toHaveText(
      `Thank you, ${firstName}. We will get back to you soon.`
    )
  }
}
