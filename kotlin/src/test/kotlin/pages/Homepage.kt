package pages

import com.microsoft.playwright.Locator.GetByRoleOptions
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.microsoft.playwright.options.AriaRole.*
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE

class Homepage(private val page: Page) {
    fun `accept cookies`(): Homepage {
        page.getByRole(BUTTON, Page.GetByRoleOptions().setName("Accept")).click()
        return this
    }

    fun `close privacy policy`(): Homepage {
        val policyIframe = page.frameLocator("iframe[title='Popup CTA']")
        policyIframe.locator("css=.button-container").click()
        return this
    }

    fun `pick SecureVault solution`(): SecureVault {
        val nav = page.getByRole(NAVIGATION)
        nav.getByRole(BUTTON, GetByRoleOptions().setName("Solutions")).hover()
        nav.getByRole(LINK, GetByRoleOptions().setName("SecureVault")).click()
        assertThat(page).hasTitle("SecureVault")
        return SecureVault(page)
    }

    companion object {
        // The fictional site in ../site, opened straight from disk (Gradle runs the tests from kotlin/)
        private val homepageUrl = Paths.get("..", "site", "index.html").toAbsolutePath().normalize().toUri().toString()

        fun navigate(page: Page): Homepage {
            page.navigate(homepageUrl)
            assertThat(page).hasTitle(
                Pattern.compile(
                    "Acme Cloud – Simple and secure file sharing",
                    CASE_INSENSITIVE
                )
            );
            return Homepage(page)
        }
    }
}

