package utils;

public class TestData {
    public static final String FIRST_NAME = "Aditya";
    public static String LAST_NAME = "Singh";
    public static String PHONE = "9999999999";
    public static String PASSWORD = "Test@123";

    public static final String EXISTING_EMAIL = "tester001@test.com";
    public static final String EXISTING_EMAIL02 = "tester002@test.com";
    public static final String EXISTING_EMAIL03 = "tester003@test.com";

    public static final String INVALID_EMAIL = "invaliduser@mail.com";
    public static final String INVALID_PASSWORD = "WrongPass123";

    public static final String VALID_PRODUCT = "iPhone";
    public static final String INVALID_PRODUCT = "NonExistingProduct123";
    public static final String PARTIAL_PRODUCT = "iPh";

    public static final String CATEGORY = "Laptops & Notebooks";
    public static final String CATEGORY_PRODUCT = "Mac";

    public static final String DESCRIPTION_PRODUCT = "iPhone";

    public static final String PRODUCT_NAME = "iPhone";

    public static final String REVIEW_NAME = "Aditya";
    public static final String REVIEW_TEXT = "Excellent product with great performance and camera quality.";

    public static final String PRODUCT_1 = "iPhone";
    public static final String PRODUCT_2 = "MacBook";

    public static String generateEmail() {
        return "user" + System.currentTimeMillis() + "@mail.com";
    }
}
