import { test } from "@playwright/test"
import { Homepage } from "./pages/homepage"

test("send inquiry about SecureVault", async ({ page }) => {
  const homepage = await Homepage.visit(page)
  const secureVault = await homepage.exploreSecureVault()
  await secureVault.sendInquiry({
    firstName: "User",
    lastName: "Test",
    email: "test@example.com",
    phoneNumber: "+351900000000",
    message: "I want to know more about..",
    marketingEmails: true,
  })
  await secureVault.confirmsInquiryFrom("User")
})
