package pages

import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GetByRoleOptions
import com.microsoft.playwright.options.AriaRole.LINK
import components.InquiryForm

class SecureVault(private val page: Page) {

    fun `go to inquiry form`(): SecureVault {
        page.getByRole(LINK, GetByRoleOptions().setName("Send inquiry")).click()
        return this
    }

    fun `submit inquiry form`(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        message: String
    ): SecureVault {
        InquiryForm(page).invoke(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            message = message,
        )
        return this
    }
}

