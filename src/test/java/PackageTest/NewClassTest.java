package PackageTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class NewClassTest {
  
	WebDriver driver;
	@Test
    public void f() {
		
		System.out.println("First change");
		System.out.println("Second change");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  driver.findElement(By.id("sb_ifc0")).click();
  driver.findElement(By.id("sb_ifc0")).sendKeys("Rest vs postman");
  driver.findElement(By.name("btnG")).click();
  System.out.println("Element searched");
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  driver = new FirefoxDriver();
	  driver.get("http://www.google.com");
	  
  }

  @AfterMethod
  public void afterMethod() {
  driver.quit();
  }

  
}
