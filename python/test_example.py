import re
from pathlib import Path

from playwright.sync_api import Page, expect

# The fictional site in ../site, opened straight from disk
HOMEPAGE_URL = (Path(__file__).parent.parent / "site" / "index.html").resolve().as_uri()


def test_send_inquiry_about_secure_vault(page: Page):
    homepage = Homepage \
        .navigate(page) \
        .accept_cookies() \
        .close_privacy_policy()
    homepage \
        .pick_secure_vault_solution() \
        .go_to_inquiry_form() \
        .submit_inquiry_form(
            first_name="User",
            last_name="Test",
            email="test@example.com",
            phone_number="+351900000000",
            message="I want to know more about..",
        ) \
        .expect_confirmation("User")


class Homepage:
    def __init__(self, page: Page):
        self.page = page

    def accept_cookies(self) -> 'Homepage':
        self.page.get_by_role("button", name="Accept").click()
        return self

    def close_privacy_policy(self) -> 'Homepage':
        policy_iframe = self.page.frame_locator("iframe[title='Popup CTA']")
        policy_iframe.locator(".button-container").click()
        return self

    def pick_secure_vault_solution(self) -> 'SecureVault':
        nav = self.page.get_by_role("navigation")
        nav.get_by_role("button", name="Solutions").hover()
        nav.get_by_role("link", name="SecureVault").click()
        expect(self.page).to_have_title("SecureVault")
        return SecureVault(self.page)

    @staticmethod
    def navigate(page: Page) -> 'Homepage':
        page.goto(HOMEPAGE_URL)
        expect(page).to_have_title(re.compile("Acme Cloud – Simple and secure file sharing", re.IGNORECASE))
        return Homepage(page)


class SecureVault:
    def __init__(self, page: Page):
        self.page = page

    def go_to_inquiry_form(self) -> 'SecureVault':
        self.page.get_by_role("link", name="Send inquiry").click()
        return self

    def submit_inquiry_form(
        self, first_name, last_name, email, phone_number, message,
    ) -> 'SecureVault':
        InquiryForm(self.page).submit(first_name, last_name, email, phone_number, message)
        return self

    def expect_confirmation(self, first_name) -> 'SecureVault':
        expect(self.page.get_by_role("status")).to_have_text(
            f"Thank you, {first_name}. We will get back to you soon."
        )
        return self


class InquiryForm:
    def __init__(self, page: Page):
        self.page = page

    def submit(self, first_name, last_name, email, phone_number, message):
        self.page.get_by_role("textbox", name=re.compile("first name", re.IGNORECASE)).fill(first_name)
        self.page.get_by_role("textbox", name=re.compile("last name", re.IGNORECASE)).fill(last_name)
        self.page.get_by_role("textbox", name=re.compile("email", re.IGNORECASE)).fill(email)
        self.page.get_by_role("textbox", name=re.compile("phone number", re.IGNORECASE)).fill(phone_number)
        self.page.get_by_role("textbox", name=re.compile("message", re.IGNORECASE)).fill(message)
        self.page.get_by_role(
            "checkbox", name=re.compile("i agree to receive marketing communications from Acme Cloud", re.IGNORECASE)
        ).click()
        self.page.get_by_role(
            "checkbox", name=re.compile("I agree to allow Acme Cloud to store and process my personal data", re.IGNORECASE)
        ).click()
        self.page.get_by_role("button", name="Submit").click()
