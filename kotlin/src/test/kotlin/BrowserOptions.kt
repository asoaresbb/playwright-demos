import com.microsoft.playwright.junit.Options
import com.microsoft.playwright.junit.OptionsFactory

// Picks the browser from the "browser" system property, set per Gradle test task in build.gradle.kts
class BrowserOptions : OptionsFactory {
    override fun getOptions(): Options = Options().setBrowserName(System.getProperty("browser", "chromium"))
}
