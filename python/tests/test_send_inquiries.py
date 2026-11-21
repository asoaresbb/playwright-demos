from components.inquiry_form import Inquiry
from pages.homepage import Homepage
from playwright.sync_api import Page


def test_send_inquiry_about_secure_vault(page: Page):
    Homepage \
        .visit(page) \
        .explore_secure_vault() \
        .send_inquiry(Inquiry(
            first_name="User",
            last_name="Test",
            email="test@example.com",
            phone_number="+351900000000",
            message="I want to know more about..",
            marketing_emails=True,
        )) \
        .confirms_inquiry_from("User")
