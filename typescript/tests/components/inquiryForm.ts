import { Page } from "@playwright/test"

export type Inquiry = {
  firstName: string
  lastName: string
  email: string
  phoneNumber: string
  message: string
  marketingEmails: boolean
}

export class InquiryForm {
  constructor(private page: Page) {}

  async submit(inquiry: Inquiry) {
    await this.textbox("first name").fill(inquiry.firstName)
    await this.textbox("last name").fill(inquiry.lastName)
    await this.textbox("email").fill(inquiry.email)
    await this.textbox("phone number").fill(inquiry.phoneNumber)
    await this.textbox("message").fill(inquiry.message)
    await this.checkbox("I agree to receive marketing communications from Acme Cloud").setChecked(inquiry.marketingEmails)
    await this.checkbox("I agree to allow Acme Cloud to store and process my personal data").check()
    await this.page.getByRole("button", { name: "Submit" }).click()
  }

  private textbox(label: string) {
    return this.page.getByRole("textbox", { name: new RegExp(label, "i") })
  }

  private checkbox(label: string) {
    return this.page.getByRole("checkbox", { name: new RegExp(label, "i") })
  }
}
