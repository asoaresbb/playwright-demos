from playwright.sync_api import Page, expect

from components.inquiry_form import Inquiry, InquiryForm


class SecureVault:
    def __init__(self, page: Page):
        self.page = page

    def send_inquiry(self, inquiry: Inquiry) -> 'SecureVault':
        """Opens the inquiry form, fills it in, gives consent and sends it."""
        self.page.get_by_role("link", name="Send inquiry").click()
        InquiryForm(self.page).submit(inquiry)
        return self

    def confirms_inquiry_from(self, first_name: str) -> 'SecureVault':
        expect(self.page.get_by_role("status")).to_have_text(
            f"Thank you, {first_name}. We will get back to you soon."
        )
        return self
