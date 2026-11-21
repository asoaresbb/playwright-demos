package components

import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GetByRoleOptions
import com.microsoft.playwright.options.AriaRole.BUTTON
import com.microsoft.playwright.options.AriaRole.CHECKBOX
import com.microsoft.playwright.options.AriaRole.TEXTBOX
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE

data class Inquiry(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val message: String,
    val marketingEmails: Boolean,
)

class InquiryForm(private val page: Page) {

    fun submit(inquiry: Inquiry) {
        textbox("first name").fill(inquiry.firstName)
        textbox("last name").fill(inquiry.lastName)
        textbox("email").fill(inquiry.email)
        textbox("phone number").fill(inquiry.phoneNumber)
        textbox("message").fill(inquiry.message)
        checkbox("I agree to receive marketing communications from Acme Cloud").setChecked(inquiry.marketingEmails)
        checkbox("I agree to allow Acme Cloud to store and process my personal data").check()
        page.getByRole(BUTTON, GetByRoleOptions().setName("Submit")).click()
    }

    private fun textbox(label: String) =
        page.getByRole(TEXTBOX, GetByRoleOptions().setName(Pattern.compile(label, CASE_INSENSITIVE)))

    private fun checkbox(label: String) =
        page.getByRole(CHECKBOX, GetByRoleOptions().setName(Pattern.compile(label, CASE_INSENSITIVE)))
}
