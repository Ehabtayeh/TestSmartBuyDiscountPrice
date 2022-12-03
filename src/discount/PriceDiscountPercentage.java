package discount;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PriceDiscountPercentage {
	public WebDriver driver;
	public int numberOfTry = 10;
	SoftAssert softassert = new SoftAssert();

	@BeforeTest
	public void BeforeMyTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://smartbuy-me.com/smartbuystore");
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();
	}

	@Test()
	public void test_DELL_G15() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		for (int i = 0; i < numberOfTry; i++) {
			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			WebElement msg = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]"));
			// System.out.println(msg);
			if (msg.getText().contains("Sorry")) {
				numberOfTry = i;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
				// System.out.println(i);

			} else {
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();
			}
		}
	}

	@Test()
	public void test_Discount() {
		driver.navigate().back();
		String price_Before_Discount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
		String[] price_Before_Discount_Split = price_Before_Discount.split("JOD");
		String price_Before_Discount_Trim = price_Before_Discount_Split[0].trim();
		String price_Before_Discount_After_Replace = price_Before_Discount_Trim.replace(",", ".");
		double final_Price_BeforeDiscount = Double.parseDouble(price_Before_Discount_After_Replace);

		System.out.println(" the price Before Discount " + " =" + final_Price_BeforeDiscount);
		String price_One_Item_AfterDiscount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] price_One_Item_AfterDiscount_Spilt = price_One_Item_AfterDiscount.split("JOD");
		String price_One_Item_AfterDiscount_replace = price_One_Item_AfterDiscount_Spilt[0].trim();
		String Update_Price = price_One_Item_AfterDiscount_replace.replace(",", ".");
		double price_One_Item_AfterDiscount_final_Price = Double.parseDouble(Update_Price);
		System.out.println(" the price After Discount " + " = " + price_One_Item_AfterDiscount_final_Price);

		String Discount_Percentage = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();
		System.out.println(" Discount_Percentage " + " =" + Discount_Percentage);
		String[] Discount_Percentage_Split = Discount_Percentage.split("%");
		String Discount_Percentage_Trim = Discount_Percentage_Split[0].trim();
		String Discount_Percentage_Replace = Discount_Percentage_Trim.replace(",", ".");
		double Discount_Percentage_Final = Double.parseDouble(Discount_Percentage_Replace);
		System.out.println(" Discount_Percentage " + " =" + Discount_Percentage_Final);
		double discountPircentage = (final_Price_BeforeDiscount * Discount_Percentage_Final) / 100;

		System.out.println("discountPircentage" + " = " + discountPircentage);
		double theLastDiscount = final_Price_BeforeDiscount - discountPircentage;
		// double a = theLastDiscount;

		System.out.println("theLastDiscount" + " = " + theLastDiscount);

		softassert.assertEquals(theLastDiscount, 1.4489345999999999, "");
		softassert.assertAll();

	}

}