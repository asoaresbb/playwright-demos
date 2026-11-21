import { expect, Page } from "@playwright/test"
import { InquiryForm } from "../components/inquiryForm.ts"
import { step } from "../testStep.ts"

export class SecureVault {
  inquiryForm: InquiryForm

  constructor(private page: Page) {
    this.inquiryForm = new InquiryForm(this.page)
  }

  @step
  async goToInquiryForm() {
    await this.page.getByRole("link", { name: "Send inquiry" }).click()
  }

  @step
  async fillInquiryForm(inquiryData: {
    firstName: string
    lastName: string
    email: string
    phoneNumber: string
    message: string
  }) {
    await this.inquiryForm.fillInquiryForm(inquiryData)
  }

  @step
  async expectConfirmation(firstName: string) {
    await expect(this.page.getByRole("status")).toHaveText(
      `Thank you, ${firstName}. We will get back to you soon.`
    )
  }
}
