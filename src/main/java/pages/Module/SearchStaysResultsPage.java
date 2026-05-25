package pages.Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import java.util.List;

public class SearchStaysResultsPage extends BasePage {

    // ================== LOCATORS (FIELDS) ==================
    // Side filter elements
    private final By progressActivity = By.className("progress-activity");
    private final By priceSectionButton = By.xpath("//button[contains(., 'Price Range')]");
    private final By priceSliderContainer = By.xpath("//div[@id='priceSlider']");
    private final By minPriceHandle = By.xpath("//div[@id='priceSlider']//div[@data-handle='0']");
    private final By maxPriceHandle = By.xpath("//div[@id='priceSlider']//div[@data-handle='1']");
    private final By innerDestinationInput = By.xpath("//input[@x-ref='destinationInput']");
    private final By innerCheckInInput = By.name("checkin_date");
    private final By innerCheckOutInput = By.name("checkout_date");
    private final By innerSearchButton = By.xpath("//form[contains(@action, '/hotels')]//button[@type='submit' or contains(., 'Search')]");
    private final By sortBySelect = By.xpath("//select[@x-model='sortBy']");
    private final By nameSearchAccordionButton = By.xpath("//button[contains(., 'Search by Name')]");
    private final By hotelNameInputField = By.xpath("//input[@placeholder='Type Hotel Name...']");
    private final By hotelCards = By.xpath("//div[contains(@class, 'flex flex-col md:flex-row')]");

    // ================== CONSTRUCTOR ==================
    public SearchStaysResultsPage(WebDriver driver) {
        // استدعاء الأب يرث ويفعل السائق، الانتظار، والـ Actions تلقائياً
        super(driver);
    }

    // ================== ACTIONS (METHODS) ==================

    public void waitForResultsToFullyLoad() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(progressActivity));
        } catch (Exception e) {
            // تجاهل الاستثناء في حال اختفائه مسبقاً
        }
    }

    public void filterByAccommodationType(String type) {
        waitForResultsToFullyLoad();
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='" + type + "']")));
        actions.scrollToElement(label).perform();
        actions.moveToElement(label).click().perform();
        waitForResultsToFullyLoad();
    }

    public void filterByPriceRange(int minXPixels, int maxXPixels) {
        waitForResultsToFullyLoad();
        WebElement priceButton = wait.until(ExpectedConditions.elementToBeClickable(priceSectionButton));
        actions.scrollToElement(priceButton).perform();

        boolean isSliderVisible = false;
        try {
            isSliderVisible = driver.findElement(priceSliderContainer).isDisplayed();
        } catch (Exception e) {}

        if (!isSliderVisible) {
            actions.moveToElement(priceButton).click().perform();
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        if (minXPixels != 0) {
            WebElement minHandle = wait.until(ExpectedConditions.visibilityOfElementLocated(minPriceHandle));
            actions.dragAndDropBy(minHandle, minXPixels, 0).perform();
        }

        if (maxXPixels != 0) {
            WebElement maxHandle = wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceHandle));
            actions.dragAndDropBy(maxHandle, maxXPixels, 0).perform();
        }
        waitForResultsToFullyLoad();
    }

    public void modifyDestination(String newDestination) {
        WebElement destInput = wait.until(ExpectedConditions.visibilityOfElementLocated(innerDestinationInput));
        destInput.clear();
        destInput.sendKeys(newDestination);
    }

    public void clickInnerSearch() {
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(innerSearchButton));
        actions.scrollToElement(searchBtn).perform();
        searchBtn.click();
        waitForResultsToFullyLoad();
    }

    public void selectSortByOption(String optionValue) {
        waitForResultsToFullyLoad();
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sortBySelect));
        Select sortDropdown = new Select(selectElement);
        sortDropdown.selectByValue(optionValue);
        System.out.println("Success: Sorted results by: " + optionValue);
        waitForResultsToFullyLoad();
    }

    public void searchByHotelNameFromSidebar(String hotelName) {
        waitForResultsToFullyLoad();
        WebElement accordionBtn = wait.until(ExpectedConditions.elementToBeClickable(nameSearchAccordionButton));
        actions.scrollToElement(accordionBtn).perform();

        boolean isInputVisible = false;
        try {
            isInputVisible = driver.findElement(hotelNameInputField).isDisplayed();
        } catch (Exception e) {
            isInputVisible = false;
        }

        if (!isInputVisible) {
            actions.moveToElement(accordionBtn).click().perform();
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(hotelNameInputField));
        inputField.clear();
        inputField.sendKeys(hotelName);
        System.out.println("Success: Typed hotel name in sidebar: " + hotelName);
        waitForResultsToFullyLoad();
    }

    public int getDisplayedHotelsCount() {
        waitForResultsToFullyLoad();
        List<WebElement> hotels = driver.findElements(hotelCards);
        return hotels.size();
    }

    public boolean selectHotelByName(String hotelName) {
        waitForResultsToFullyLoad();
        List<WebElement> hotels = driver.findElements(hotelCards);

        for (WebElement hotel : hotels) {
            try {
                WebElement nameElement = hotel.findElement(By.xpath(".//h3[contains(@class, 'font-bold')]"));
                String currentHotelName = nameElement.getText().trim();

                if (currentHotelName.equalsIgnoreCase(hotelName)) {
                    WebElement detailsBtn = hotel.findElement(By.xpath(".//a[contains(@class, 'btn')]"));
                    actions.scrollToElement(detailsBtn).moveToElement(detailsBtn).click().perform();
                    System.out.println("Success: Found and clicked on hotel: " + hotelName);
                    return true;
                }
            } catch (Exception e) {
                // ممر التخطي الآمن
            }
        }
        System.out.println("Centered Note: Hotel '" + hotelName + "' was not found in the results.");
        return false;
    }
}