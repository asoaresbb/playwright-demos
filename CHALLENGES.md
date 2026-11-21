# Practice challenges

The fictional Acme Cloud site in [`site/`](site/) has more pages than the example test uses. Each one is built around patterns you meet when testing real web apps. Pick a language folder, add a page object or two, and write tests for the challenges below.

The example test (`sendInquiries`) already covers the homepage, the cookie banner, the popup in an iframe, the hover menu and the inquiry form. Use it as a model.

## Before you start

- **Opening the site.** The pages open straight from disk. Each language folder already builds the path to `site/`: reuse that, or point your tests at `site/<page>.html`.
- **Locators.** Use them in the order the Playwright docs recommend: `getByRole`, then `getByLabel`, `getByPlaceholder`, `getByText`, `getByTitle`, and CSS only as a last resort. Every challenge here can be solved without CSS selectors.
- **Data.** All data is made up and fixed, so results are the same on every run. Changes you make (deleting files, sharing) live only in the page and reset when it reloads.
- **Demo account.** The sign-in page accepts one account:
  - Email: `ines.duarte@acmecloud.test`
  - Password: `vault-demo-2024`

Levels: **B** beginner, **I** intermediate, **A** advanced.

## Pricing (`pricing.html`)

| Level | Challenge | What it practises |
| --- | --- | --- |
| B | Check that the three plans show €12, €29 and "Custom". | Scoping a locator to one `article` by its name |
| B | Turn on "Bill yearly" and check that Business shows €23.20 and mentions the yearly total. | `role="switch"`, `aria-checked` |
| I | In the calculator, pick Starter, 25 users and yearly billing, and check the total. | `selectOption`, `fill`, `<output>` |
| I | Enter 600 users and check the error. Then enter 0 and check the other error. | Validation messages with `role="alert"` |
| I | Check that SSO is included only in Enterprise, by reading the comparison table. | Table rows and cells by role and name |
| B | Open "What happens when the trial ends?" and check the answer is visible; then close it. | `<details>` and `<summary>` |

## Customers (`customers.html`)

| Level | Challenge | What it practises |
| --- | --- | --- |
| B | Check the page starts with "Showing 6 of 18 customers". | `role="status"` |
| I | Click "Load more" twice and check all 18 are listed and the button is gone. | Content that appears after a delay, auto-waiting |
| B | Filter by Healthcare and check only healthcare customers remain. | Toggle buttons with `aria-pressed` |
| I | Search for "Portugal", then filter by Legal, and check the empty state appears. Clear the filters. | Combining filters, empty states |
| I | Load more customers, open the story of Aurelia Bank and check the number of users in the dialog. Close it with the button, then open another and close it with Escape. | Modal `<dialog>`, keyboard |
| A | Check that every visible customer has a quote and a "Read story" button. | Iterating over a list of locators |

## Sign in and files (`app.html`)

| Level | Challenge | What it practises |
| --- | --- | --- |
| B | Submit the empty form and check both field errors. | Form validation |
| B | Sign in with the demo account and check the "Files" heading and the page title. | A full login flow |
| I | Get the password wrong three times and check the form locks and counts down. | Timers; try `page.clock` to skip the 30 seconds |
| I | Click "Show" and check the password becomes readable text. | Attributes and `aria-pressed` |
| A | Sign in once with "Keep me signed in", save the storage state, and reuse it in other tests so they start signed in. | `storageState`, test fixtures |
| I | Sort by size, largest first, and check the first row. | Sortable headers with `aria-sort` |
| I | Filter by "pdf" and check how many rows remain. | Counting rows |
| I | Share "Board minutes – March.docx" with someone for 30 days, with downloads allowed. Check the toast and the new "Shared with" count. | Dialog forms, toasts |
| I | Try to share without an email address and check the error. | Validation inside a dialog |
| I | Delete one file with its "Delete" button and confirm in the dialog. Then cancel a second deletion and check the file is still there. | Confirmation dialogs |
| A | Select two files and use "Delete selected". It asks with the browser's own `confirm()`: accept it once and dismiss it once. | `page.on("dialog")`, `accept` and `dismiss` |
| I | Use "Select all", then untick one file, and check the "Select all" box shows as mixed. | Indeterminate checkboxes |
| A | Upload two files at once and check both appear with their sizes. | `setInputFiles` with in-memory files |
| I | Open the account menu, check it with the keyboard (Escape closes it), and sign out. | `role="menu"`, focus |
| B | After signing in, check the header shows "Files" and "Sign out" instead of "Sign in". Sign out from the header and check it changes back. | Scoping to the `navigation` landmark |

## Audit Log (`audit-log.html`)

| Level | Challenge | What it practises |
| --- | --- | --- |
| B | Wait for the events to load and check the table shows 10 rows. | Loading states, auto-waiting |
| I | Go to the last page with "Next" and check "Next" is disabled there. | Pagination, disabled buttons |
| I | Show only "Revoked" events by Piotr Nowak and check every row matches. | Several filters at once |
| I | Set a start date after the end date and check the error. | Date inputs |
| A | Export the CSV and check its file name, its header line and that it has one line per filtered event. | Downloads, reading the file |

## Legal (`legal.html`)

| Level | Challenge | What it practises |
| --- | --- | --- |
| B | Open `legal.html#security` and check the Security tab is selected. | URLs with a hash |
| I | Move between the tabs with the arrow keys, Home and End, and check which panel is visible. | `role="tablist"`, keyboard |
| B | Check the subprocessors table lists a data centre in Zurich. | Tables |
| B | From any page, follow the "Terms" link in the footer and check the Terms tab opens. | `role="contentinfo"` for the footer |

## Across the site

| Level | Challenge | What it practises |
| --- | --- | --- |
| I | Run an accessibility check on every page and report the violations it finds. | `@axe-core/playwright` or your language's equivalent |
| I | Take a screenshot of the pricing page and compare it on later runs. | Visual comparisons |
| A | Run the same test at a phone viewport and check the menu still works. | Viewports and device emulation |
