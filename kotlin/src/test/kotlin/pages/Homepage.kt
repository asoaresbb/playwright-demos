package pages

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.microsoft.playwright.options.AriaRole.BUTTON
import com.microsoft.playwright.options.AriaRole.LINK
import com.microsoft.playwright.options.AriaRole.NAVIGATION
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.regex.Pattern.CASE_INSENSITIVE

class Homepage private constructor(private val page: Page) {

    fun `explore SecureVault`(): SecureVault {
        val nav = page.getByRole(NAVIGATION)
        nav.getByRole(BUTTON, Locator.GetByRoleOptions().setName("Solutions")).hover()
        nav.getByRole(LINK, Locator.GetByRoleOptions().setName("SecureVault")).click()
        assertThat(page).hasTitle("SecureVault")
        return SecureVault(page)
    }

    private fun `accept cookies`() {
        page.getByRole(BUTTON, Page.GetByRoleOptions().setName("Accept")).click()
    }

    private fun `dismiss privacy notice`() {
        val notice = page.getByTitle("Popup CTA").contentFrame()
        notice.getByRole(BUTTON, FrameLocator.GetByRoleOptions().setName("Got it")).click()
    }

    companion object {
        // The fictional site in ../website, opened straight from disk (Gradle runs the tests from kotlin/)
        private val homepageUrl = Paths.get("..", "website", "index.html").toAbsolutePath().normalize().toUri().toString()

        // Opens the site and gets past the cookie banner and the privacy notice
        fun visit(page: Page): Homepage {
            page.navigate(homepageUrl)
            assertThat(page).hasTitle(Pattern.compile("Acme Cloud – Simple and secure file sharing", CASE_INSENSITIVE))
            return Homepage(page).apply {
                `accept cookies`()
                `dismiss privacy notice`()
            }
        }
    }
}
