import re
from dataclasses import dataclass

from playwright.sync_api import Page


@dataclass
class Inquiry:
    first_name: str
    last_name: str
    email: str
    phone_number: str
    message: str
    marketing_emails: bool


class InquiryForm:
    def __init__(self, page: Page):
        self.page = page

    def submit(self, inquiry: Inquiry):
        self._textbox("first name").fill(inquiry.first_name)
        self._textbox("last name").fill(inquiry.last_name)
        self._textbox("email").fill(inquiry.email)
        self._textbox("phone number").fill(inquiry.phone_number)
        self._textbox("message").fill(inquiry.message)
        self._checkbox("I agree to receive marketing communications from Acme Cloud").set_checked(inquiry.marketing_emails)
        self._checkbox("I agree to allow Acme Cloud to store and process my personal data").check()
        self.page.get_by_role("button", name="Submit").click()

    def _textbox(self, label: str):
        return self.page.get_by_role("textbox", name=re.compile(label, re.IGNORECASE))

    def _checkbox(self, label: str):
        return self.page.get_by_role("checkbox", name=re.compile(label, re.IGNORECASE))
