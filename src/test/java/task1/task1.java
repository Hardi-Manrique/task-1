package task1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class task1 {
    public WebDriver driver;

    //Declare variables and information for purchase
    public String fullName = "Tester QA", email, password = "Abcd@123";
    public String telephone = "7865974876", address = "9370 E Bay Harbor Dr", city = "Miami", country = "United States", province = "Florida", postcode = "33154";

    public String card = "4242 4242 4242 4242", expiry = "04/24", cvc = "242";
    public String testEmail = "test@email.com", testPassword = "Abcd@1234";

    public String prod1, prod2, prod3;

    public WebDriverWait wait;

    @BeforeSuite
    public void startSuite() throws Exception {
    }

    @AfterSuite
    public void stopSuite() throws Exception {
        System.out.println("All done!!!");
    }

    @BeforeClass
    public void startClass() throws Exception {
        //Initiate driver, set timeout duration and maximize window
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void stopSelenium() {
        driver.quit();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws Exception {
        System.out.println("------------------------------------------------------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        System.out.println("------------------------------------------------------------------------");
    }

    @Test(priority = 1)
    public void goToEvershop() throws Exception {
        //Open Evershop demo in chrome driver
        driver.get("https://demo.evershop.io/");
        System.out.println("Opened EverShop");
    }

    @Test(priority = 2)
    public void verifyEverShopTitle() throws Exception {
        //Validate site title using Selenium assert
        Assert.assertEquals(driver.getTitle(), "An Amazing EverShop Store");
        System.out.println("Verified site title");
        ;
    }

    @Test(priority = 3)
    public void navigateToSignIn() throws Exception {
        //Locate sign in button on home page and click to navigate to sign in
        WebElement signInBtn = driver.findElement(By.cssSelector("a[href=\"/account/login\"]"));
        Assert.assertTrue(signInBtn.isDisplayed());
        signInBtn.click();
        System.out.println("Clicked sign in button");
    }

    @Test(priority = 4)
    public void navigateToSignUp() throws Exception {
        //Locate sign up button on home page and click to navigate to sign up
        WebElement signUpBtn = driver.findElement(By.cssSelector("a[href=\"/account/register\"]"));
        Assert.assertEquals(driver.getTitle(), "Login");
        Assert.assertTrue(signUpBtn.isDisplayed());
        signUpBtn.click();
    }

    @Test(priority = 5)
    public void signUp() throws Exception {
        //Locate sign up form elements
        WebElement fullNameInput = driver.findElement(By.name("full_name"));
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement submitBtn = driver.findElement(By.className("form-submit-button"));

        //Validate they are visible
        Assert.assertTrue(fullNameInput.isDisplayed());
        Assert.assertTrue(emailInput.isDisplayed());
        Assert.assertTrue(passwordInput.isDisplayed());
        Assert.assertTrue(submitBtn.isDisplayed());

        //Insert sign up information and sign up
        email="testqa-"+ UUID.randomUUID() +"@email.com";
        System.out.println("New Email: "+email);
        fullNameInput.sendKeys(fullName);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        submitBtn.click();

        //Validate user is redirected to home after successful sign up
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("main-banner-home")));
        System.out.println("Signed Up with login info");
    }

    @Test(priority = 6)
    public void addProducts() throws Exception {
        //Navigate to mens section
        WebElement menSectionNavLink = driver.findElement(By.cssSelector(".nav-item a[href=\"/category/men\"]"));
        Assert.assertTrue(menSectionNavLink.isDisplayed());
        menSectionNavLink.click();

        //Validate user has arrived at mens section
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("listing-tem")));

        //Add 3 different products and quantities
        addIndividualProduct(driver.findElement(By.cssSelector(".listing-tem:nth-child(1)")), 5, 1);
        addIndividualProduct(driver.findElement(By.cssSelector(".listing-tem:nth-child(2)")), 2, 2);
        addIndividualProduct(driver.findElement(By.cssSelector(".listing-tem:nth-child(3)")), 3, 3);
    }

    @Test(priority = 7)
    public void checkoutProduct() throws Exception {
        //Click on cart btn to navigate to cart
        WebElement cartBtn = driver.findElement(By.className("mini-cart-icon"));
        Assert.assertTrue(cartBtn.isDisplayed());
        cartBtn.click();

        //Click on checkout to navigate to payment
        WebElement checkoutBtn = driver.findElement(By.className("shopping-cart-checkout-btn"));
        checkoutBtn.click();

        //Locate address form elements
        WebElement fullNameInput = driver.findElement(By.name("address[full_name]"));
        WebElement telephoneInput = driver.findElement(By.name("address[telephone]"));
        WebElement addressInput = driver.findElement(By.name("address[address_1]"));
        WebElement cityInput = driver.findElement(By.name("address[city]"));
        Select countrySelect = new Select(driver.findElement(By.name("address[country]")));
        Select provinceSelect = new Select(driver.findElement(By.name("address[province]")));
        WebElement postcodeInput = driver.findElement(By.name("address[postcode]"));

        //Insert Address information
        fullNameInput.sendKeys(fullName);
        telephoneInput.sendKeys(telephone);
        addressInput.sendKeys(address);
        cityInput.sendKeys(city);
        countrySelect.selectByVisibleText(country);
        provinceSelect.selectByVisibleText(province);
        postcodeInput.sendKeys(postcode);

        //Select shipping option and submit
        WebElement shippingOption1 = driver.findElement(By.cssSelector("#method0 + span"));
        WebElement submitButton = driver.findElement(By.cssSelector(".form-submit-button button"));

        shippingOption1.click();
        submitButton.click();

        sleep(500);

        //Select payment option
        WebElement cardPaymentOption = driver.findElement(By.cssSelector(".payment-method-list:nth-child(3) a"));
        cardPaymentOption.click();

        //Switch frame to target iFrame elements for credit card
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));

        //Locate payment info elements
        WebElement cardNumberInput = driver.findElement(By.name("cardnumber"));
        WebElement expInput = driver.findElement(By.name("exp-date"));
        WebElement cvcInput = driver.findElement(By.name("cvc"));

        //Insert payment info
        cardNumberInput.sendKeys(card);
        expInput.sendKeys(expiry);
        cvcInput.sendKeys(cvc);

        //Switch back to original frame
        driver.switchTo().defaultContent();

        //Locate and click on order button
        WebElement orderButton = driver.findElement(By.cssSelector(".form-submit-button button"));
        orderButton.click();
    }

    @Test(priority = 8)
    public void validateOrder() {

        // Locate and validate contact and payment info matches the one used
        WebElement contactInfo = driver.findElement(By.xpath("(//div[@class=\"text-textSubdued\"])[1]"));
        WebElement paymentMethod = driver.findElement(By.xpath("(//div[@class=\"text-textSubdued\"])[3]"));

        Assert.assertEquals(contactInfo.getText(), email);
        Assert.assertEquals(paymentMethod.getText(), "Credit Card");

        System.out.println("Validated Contact Info and Payment Method");

        // Locate and validate sipping info matches the one used
        WebElement shippingFullName = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(1) .full-name"));
        WebElement shippingAddress = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(1) .address-one"));
        WebElement shippingCityPostcode = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(1) .city-province-postcode > div:nth-child(1)"));
        WebElement shippingProvinceCountry = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(1) .city-province-postcode > div:nth-child(2)"));
        WebElement shippingTelephone = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(1) .telephone"));

        Assert.assertEquals(shippingFullName.getText(), fullName);
        Assert.assertEquals(shippingAddress.getText(), address);
        Assert.assertEquals(shippingCityPostcode.getText(), postcode + ", " + city);
        Assert.assertEquals(shippingProvinceCountry.getText(), province + ", " + country);
        Assert.assertEquals(shippingTelephone.getText(), telephone);

        System.out.println("Validated Shipping Info");

        // Locate and validate billing info matches the one used
        WebElement billingFullName = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(2) .full-name"));
        WebElement billingAddress = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(2) .address-one"));
        WebElement billingCityPostcode = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(2) .city-province-postcode > div:nth-child(1)"));
        WebElement billingProvinceCountry = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(2) .city-province-postcode > div:nth-child(2)"));
        WebElement billingTelephone = driver.findElement(By.cssSelector(".customer-info .grid > div:nth-child(2) .telephone"));

        Assert.assertEquals(billingFullName.getText(), fullName);
        Assert.assertEquals(billingAddress.getText(), address);
        Assert.assertEquals(billingCityPostcode.getText(), postcode + ", " + city);
        Assert.assertEquals(billingProvinceCountry.getText(), province + ", " + country);
        Assert.assertEquals(billingTelephone.getText(), telephone);

        System.out.println("Validated Billing Info");

        // Locate and validate product name and quantity info matches
        WebElement prod1Name = driver.findElement(By.cssSelector("tr:nth-child(1) .product-column div > span:first-child"));
        WebElement prod2Name = driver.findElement(By.cssSelector("tr:nth-child(2) .product-column div > span:first-child"));
        WebElement prod3Name = driver.findElement(By.cssSelector("tr:nth-child(3) .product-column div > span:first-child"));

        WebElement prod1Qty = driver.findElement(By.cssSelector("tr:nth-child(1) .qty"));
        WebElement prod2Qty = driver.findElement(By.cssSelector("tr:nth-child(2) .qty"));
        WebElement prod3Qty = driver.findElement(By.cssSelector("tr:nth-child(3) .qty"));

        Assert.assertEquals(prod1Name.getText().toLowerCase(),prod1);
        Assert.assertEquals(prod1Qty.getText(),"5");

        Assert.assertEquals(prod2Name.getText().toLowerCase(),prod2);
        Assert.assertEquals(prod2Qty.getText(),"2");

        Assert.assertEquals(prod3Name.getText().toLowerCase(),prod3);
        Assert.assertEquals(prod3Qty.getText(),"3");

        System.out.println("Validated Product Info");
    }

    public void addIndividualProduct(WebElement product, int qty, int prodNum) throws Exception {
        //Click on product on mens section
        product.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-single-name")));

        //Locate product name and input fields and variant buttons
        WebElement prodName = driver.findElement(By.className("product-single-name"));
        WebElement qtyInput = driver.findElement(By.name("qty"));
        WebElement addToCartBtn = driver.findElement(By.cssSelector("#productForm button"));
        WebElement firstSizeOption = driver.findElement(By.cssSelector(".variant div:nth-child(1) ul li:first-child"));
        WebElement firstColorOption = driver.findElement(By.cssSelector(".variant div:nth-child(2) ul li:first-child"));

        //Assign selected product name to variable
        switch (prodNum) {
            case 1:
                prod1 = prodName.getText().toLowerCase();
                break;
            case 2:
                prod2 = prodName.getText().toLowerCase();
                break;
            case 3:
                prod3 = prodName.getText().toLowerCase();
                break;
            default:
                break;
        }

        //insert quantity, select variant and add to cart
        qtyInput.clear();
        qtyInput.sendKeys("" + qty);
        firstSizeOption.click();
        firstColorOption.click();
        sleep(500);
        addToCartBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("toast-mini-cart")));
        System.out.println("---Product Added---");
        System.out.println("Product: "+prodName.getText());
        System.out.println("Quantity: "+qty);

        //navigate back to men section
        driver.findElement(By.cssSelector(".nav-item a[href=\"/category/men\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("listing-tem")));
    }
}
