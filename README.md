# playwright-polyglot-pom

Two things in one repository:

- **The same end-to-end test in three languages**, TypeScript, Kotlin and Python, written with Playwright and the Page Object Model, to compare how each language reads.
- **A practice website** for a fictional company, Acme Cloud, with pages built around the situations you meet when testing real web apps, and [a list of challenges](CHALLENGES.md) to solve in any language.

The website opens straight from disk: no server, no internet connection, and nothing is sent anywhere.

## The practice website

Open `website/index.html` in a browser to look around. Each page exercises different Playwright features:

| Page | What's on it |
| --- | --- |
| Home (`index.html`) | Cookie banner, a popup inside an iframe, a menu that opens on hover |
| SecureVault (`securevault.html`) | Product page with an inquiry form and a confirmation message |
| Pricing (`pricing.html`) | Monthly/yearly switch, a cost calculator with validation, a comparison table, questions that expand |
| Customers (`customers.html`) | Search, filters, "Load more" with a delay, stories in a modal dialog, an empty state |
| Sign in (`app.html`) | Login with validation and a lockout after three attempts, then a file list: sorting, filtering, selecting, sharing, deleting (with a dialog and with the browser's `confirm()`), uploading, settings |
| Audit Log (`audit-log.html`) | Events that load after a delay, filters, pagination, CSV export |
| Legal (`legal.html`) | Accessible tabs, with the URL hash picking the tab |

The pages use semantic HTML, so every challenge can be solved with the locators Playwright recommends (`getByRole`, `getByLabel`, `getByPlaceholder` and so on), without CSS selectors. The data is made up and fixed, so results are the same on every run.

[`CHALLENGES.md`](CHALLENGES.md) has about 40 exercises by page and level, and the demo account for the sign-in page.

## The example test

Each language folder has the same test, written in the user's words. The test says what the user wants to do; the page objects know how to do it on the page:

```ts
const homepage = await Homepage.visit(page)
const secureVault = await homepage.exploreSecureVault()
await secureVault.sendInquiry({ firstName: "User", /* ... */ marketingEmails: true })
await secureVault.confirmsInquiryFrom("User")
```

Each method is one intention, even when it takes several steps. `visit` opens the site and gets past the cookie banner and the privacy notice in its iframe; `exploreSecureVault` hovers over the menu, picks the product and checks the page; `sendInquiry` opens the form, fills it in, gives consent and submits it. Buttons, iframes and menus stay inside the page objects (`Homepage`, `SecureVault` and the `InquiryForm` component), so the test doesn't change when the page layout does.

| | TypeScript | Kotlin | Python |
| --- | --- | --- | --- |
| Test runner | `@playwright/test` | JUnit 5 with `@UsePlaywright` | pytest with the `page` fixture |
| Async | `await` on every action | synchronous API | synchronous API |
| Method names | `exploreSecureVault()` | `` `explore SecureVault`() ``, with backticks | `explore_secure_vault()` |
| Chaining | methods that change page return the next page object; one `await` per line | one chain from `visit` to the check | one chain from `visit` to the check |
| Inquiry data | a `type` | a `data class` | a `@dataclass` |

## Running the tests

**TypeScript** (Node.js):

```sh
cd typescript
npm ci
npx playwright install
npm test          # runs in Chromium, Firefox and WebKit
npm run report    # opens the HTML report
```

**Kotlin** (JDK 21 and Gradle):

```sh
cd kotlin
gradle playwright --args="install"
gradle test       # runs in Chromium, Firefox and WebKit (testChromium, testFirefox, testWebkit)
```

**Python** (Python 3.12; Playwright 1.65 no longer supports 3.9):

```sh
cd python
python -m venv .venv && source .venv/bin/activate
pip install -r requirements.txt
playwright install
pytest            # runs in Chromium, Firefox and WebKit (see pytest.ini)
```

The versions of the tools are in each folder's `.tool-versions`, for those who use `asdf`. The TypeScript tests also run on GitHub Actions on every push.

## Structure

```
typescript/       the example test with @playwright/test
kotlin/           the example test with Playwright for Java and JUnit 5
python/           the example test with pytest-playwright
website/          the practice website
CHALLENGES.md     exercises for the practice website
```

## Context

Personal project. The example test was written in July 2024 through pair programming with a mentor; the practice website and the challenges were added in November 2026.
