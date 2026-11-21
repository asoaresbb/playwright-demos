import com.microsoft.playwright.Page
import com.microsoft.playwright.junit.UsePlaywright
import components.Inquiry
import kotlin.test.Test
import pages.Homepage

@UsePlaywright(BrowserOptions::class)
class TestSendInquiries {

    @Test
    fun `send inquiry about SecureVault`(page: Page) {
        Homepage
            .visit(page)
            .`explore SecureVault`()
            .`send inquiry`(
                Inquiry(
                    firstName = "User",
                    lastName = "Test",
                    email = "test@example.com",
                    phoneNumber = "+351900000000",
                    message = "I want to know more about..",
                    marketingEmails = true,
                )
            )
            .`confirms inquiry from`("User")
    }
}
