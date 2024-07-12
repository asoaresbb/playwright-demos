import com.microsoft.playwright.Page
import com.microsoft.playwright.junit.UsePlaywright
import kotlin.test.Test
import pages.Homepage

@UsePlaywright
class TestSendInquiries {

    @Test
    fun `send inquiry about SecureVault`(page: Page) {
        val homepage = Homepage
            .navigate(page)
            .`accept cookies`()
            .`close privacy policy`()
        homepage
            .`pick SecureVault solution`()
            .`go to inquiry form`()
            .`submit inquiry form`(
                    firstName = "User",
                    lastName = "Test",
                    email = "test@abc.com",
                    phoneNumber = "+351900000000",
                    message = "I want to know more about..",
            )
    }
}
