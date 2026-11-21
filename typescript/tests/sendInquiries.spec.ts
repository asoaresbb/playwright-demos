import { test } from "@playwright/test"
import { Homepage } from "./pages/homepage"

test("send inquiry about SecureVault", async ({ page }) => {
  const homepage = await Homepage.navigate(page)
  await homepage.acceptCookies()
  await homepage.closePrivacyPolicy()
  const secureVault = await homepage.pickSecureVaultSolution()
  await secureVault.goToInquiryForm()
  await secureVault.fillInquiryForm({
    firstName: "User",
    lastName: "Test",
    email: "test@example.com",
    phoneNumber: "+351900000000",
    message: "I want to know more about..",
  })
  await secureVault.expectConfirmation("User")
})
