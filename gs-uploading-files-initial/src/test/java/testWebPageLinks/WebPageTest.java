package testWebPageLinks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Class: WebPageTest
 * @author Cameron Ventimiglia
 * Purpose: to test the web page and make sure the links and buttons work properly.
 */
public class WebPageTest {
	
	//initialize the WebDriver variable
	private static WebDriver driver;

	/**
	 * this method is used to set up the chrome driver
	 */
    @BeforeAll
    public static void setUpChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\camve\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    
    /**
     * this method tests the example JSON file link
     */
    @Test
    public void testJsonFileLink() {
    	driver.get("http://localhost:8080/");
    	
    	WebElement jsonFile = driver.findElement(By.linkText("Example JSON File"));
    	jsonFile.click();
    	
    }
    
    /**
     * this method tests the ggc link
     */
    @Test
    public void testGGCLink() {
    	driver.get("http://localhost:8080/");

    	WebElement ggcLink = driver.findElement(By.linkText("here"));
    	ggcLink.click();
    }
    
    /**
     * this method tests the user guide link
     */
    @Test
    public void testUserGuideLink() {
    	driver.get("http://localhost:8080/");
    	
    	WebElement userGuide = driver.findElement(By.linkText("Link to User Guide"));
    	userGuide.click();
    }
    
    /**
     * this method tests the choose file button and the upload button
     */
    @Test
    public void testUploadFile() {
    	driver.get("http://localhost:8080/");
    	
    	WebElement chooseFile = driver.findElement(By.name("file"));
    	chooseFile.sendKeys("C:\\Users\\camve\\Downloads\\CategoryPartition\\Specifications\\ExecutionQueueOnSave.json");
    	
    	driver.findElement(By.xpath("/html/body/div/main/div[2]/form/p[4]/input")).click();
    }
    
    /**
     * this method test the file download link for the text file.
     */
    @Test
    public void testDownloadLink() {
    	driver.get("http://localhost:8080/");
    	
    	WebElement chooseFile = driver.findElement(By.name("file"));
    	chooseFile.sendKeys("C:\\Users\\camve\\Downloads\\CategoryPartition\\Specifications\\ExecutionQueueOnSave.json");
    	
    	driver.findElement(By.xpath("/html/body/div/main/div[2]/form/p[4]/input")).click();
    	
    	WebElement download = driver.findElement(By.linkText("http://localhost:8080/files/ExecutionQueueOnSave-combos.txt"));
    	download.click();
    }

}
