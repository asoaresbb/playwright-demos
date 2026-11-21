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

Each language folder has the same test: it opens the homepage, accepts the cookies, closes the popup in the iframe, picks **SecureVault** from the hover menu, fills in the inquiry form and checks the confirmation. Each one uses the same page objects: `Homepage`, `SecureVault` and an `InquiryForm` component.

| | TypeScript | Kotlin | Python |
| --- | --- | --- | --- |
| Test runner | `@playwright/test` | JUnit 5 with `@UsePlaywright` | pytest with the `page` fixture |
| Async | `await` on every action | synchronous API | synchronous API |
| Method names | `acceptCookies()` | `` `accept cookies`() ``, with backticks | `accept_cookies()` |
| Chaining | navigation methods return the next page object; one `await` per line | each method returns the next page object | each method returns the next page object |

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
gradle test
```

**Python** (Python 3.12; Playwright 1.65 no longer supports 3.9):

```sh
cd python
python -m venv .venv && source .venv/bin/activate
pip install -r requirements.txt
playwright install
pytest
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
