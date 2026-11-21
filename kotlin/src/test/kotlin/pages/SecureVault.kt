package pages

import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GetByRoleOptions
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.microsoft.playwright.options.AriaRole.LINK
import com.microsoft.playwright.options.AriaRole.STATUS
import components.Inquiry
import components.InquiryForm

class SecureVault(private val page: Page) {

    // Opens the inquiry form, fills it in, gives consent and sends it
    fun `send inquiry`(inquiry: Inquiry): SecureVault {
        page.getByRole(LINK, GetByRoleOptions().setName("Send inquiry")).click()
        InquiryForm(page).submit(inquiry)
        return this
    }

    fun `confirms inquiry from`(firstName: String): SecureVault {
        assertThat(page.getByRole(STATUS)).hasText("Thank you, $firstName. We will get back to you soon.")
        return this
    }
}
