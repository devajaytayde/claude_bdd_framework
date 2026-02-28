package com.framework.pages;

import com.framework.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    private static final By PAGE_TITLE = By.className("title");
    private static final By INVENTORY_ITEMS = By.cssSelector(".inventory_item");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By ADD_TO_CART_FIRST_ITEM = By.cssSelector(".inventory_item:first-child .btn_inventory");
    private static final By CART_ICON = By.cssSelector(".shopping_cart_link");

    public InventoryPage() {
        super();
    }

    public boolean isInventoryPageDisplayed() {
        return isElementVisible(PAGE_TITLE) && getPageTitle().equals("Swag Labs");
    }

    public String getInventoryPageTitle() {
        return SeleniumUtils.getText(PAGE_TITLE);
    }

    public int getInventoryItemCount() {
        return inventoryItems.size();
    }

    public void addFirstItemToCart() {
        logger.info("Adding first item to cart");
        SeleniumUtils.click(ADD_TO_CART_FIRST_ITEM);
    }

    public String getCartBadgeCount() {
        return SeleniumUtils.getText(CART_BADGE);
    }

    public boolean isCartBadgeDisplayed() {
        return isElementVisible(CART_BADGE);
    }

    public void clickCartIcon() {
        logger.info("Clicking cart icon");
        cartIcon.click();
    }
}
