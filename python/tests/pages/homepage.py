import re
from pathlib import Path

from playwright.sync_api import Page, expect

from pages.secure_vault import SecureVault

# The fictional site in ../website, opened straight from disk
HOMEPAGE_URL = (Path(__file__).parents[3] / "website" / "index.html").resolve().as_uri()


class Homepage:
    def __init__(self, page: Page):
        self.page = page

    @staticmethod
    def visit(page: Page) -> 'Homepage':
        """Opens the site and gets past the cookie banner and the privacy notice."""
        page.goto(HOMEPAGE_URL)
        expect(page).to_have_title(re.compile("Acme Cloud – Simple and secure file sharing", re.IGNORECASE))
        homepage = Homepage(page)
        homepage._accept_cookies()
        homepage._dismiss_privacy_notice()
        return homepage

    def explore_secure_vault(self) -> SecureVault:
        nav = self.page.get_by_role("navigation")
        nav.get_by_role("button", name="Solutions").hover()
        nav.get_by_role("link", name="SecureVault").click()
        expect(self.page).to_have_title("SecureVault")
        return SecureVault(self.page)

    def _accept_cookies(self):
        self.page.get_by_role("button", name="Accept").click()

    def _dismiss_privacy_notice(self):
        notice = self.page.get_by_title("Popup CTA").content_frame
        notice.get_by_role("button", name="Got it").click()
