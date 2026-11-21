# playwright-polyglot-pom

The same end-to-end test written with Playwright in three languages, TypeScript, Kotlin and Python, to compare the syntax and how each one organises the Page Object Model.

## What the test does

The tests run against a fictional company site in [`website/`](website/), opened straight from disk, so they need no server or internet connection and send nothing anywhere. The test:

1. opens the homepage and checks its title;
2. accepts the cookie banner;
3. closes a privacy popup that lives inside an iframe;
4. hovers over the **Solutions** menu and picks the **SecureVault** product;
5. opens the inquiry form, fills in every field and both consent checkboxes, and submits it;
6. checks the confirmation message.

## Structure

```
typescript/    @playwright/test, with pages and a form component
kotlin/        Playwright for Java with JUnit 5, built with Gradle
python/        pytest-playwright, in a single file
website/       the fictional site the tests run against
```

Each language uses the same page objects: `Homepage`, `SecureVault` and an `InquiryForm` component.

## Practising

The site has more pages than the example test uses: pricing, customers, a sign-in page with a file list, an audit log and legal pages. They are built around common testing situations, such as filters, sortable tables, dialogs, delayed loading, downloads and a login. [`CHALLENGES.md`](CHALLENGES.md) lists exercises for each page, by level, to write in any of the three languages.


## What differs between the languages

| | TypeScript | Kotlin | Python |
| --- | --- | --- | --- |
| Test runner | `@playwright/test` | JUnit 5 with `@UsePlaywright` | pytest with the `page` fixture |
| Async | `await` on every action | synchronous API | synchronous API |
| Method names | `acceptCookies()` | `` `accept cookies`() ``, with backticks | `accept_cookies()` |
| Chaining | navigation methods return the next page object; one `await` per line | each method returns the next page object | each method returns the next page object |

## Running

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

The versions of the tools are in each folder's `.tool-versions`, for those who use `asdf`.

## Context

Personal project, July 2024, built through pair programming with a mentor.
