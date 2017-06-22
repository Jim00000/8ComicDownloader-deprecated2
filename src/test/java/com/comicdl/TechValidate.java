package com.comicdl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TechValidate {

	private final static Logger logger = LogManager.getLogger(TechValidate.class);

	public static void main(String[] args) throws MalformedURLException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		System.setProperty("phantomjs.binary.path", "src/resources/phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		WebElement img = null;
		driver.navigate().to("http://v.comicbus.com/online/comic-103.html?ch=471-1");
		img = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("TheImg")));
		System.out.println("Find : " + img.getAttribute("src"));
		FileUtils.copyURLToFile(new URL(img.getAttribute("src")), new File("001.jpg"));
		
		driver.navigate().to("http://v.comicbus.com/online/comic-103.html?ch=471-2");
		img = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("TheImg")));
		System.out.println("Find : " + img.getAttribute("src"));
		FileUtils.copyURLToFile(new URL(img.getAttribute("src")), new File("002.jpg"));
		
		driver.navigate().to("http://v.comicbus.com/online/comic-103.html?ch=471-3");
		img = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("TheImg")));
		System.out.println("Find : " + img.getAttribute("src"));
		FileUtils.copyURLToFile(new URL(img.getAttribute("src")), new File("003.jpg"));
		
		driver.navigate().to("http://v.comicbus.com/online/comic-103.html?ch=471-4");
		img = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("TheImg")));
		System.out.println("Find : " + img.getAttribute("src"));
		FileUtils.copyURLToFile(new URL(img.getAttribute("src")), new File("004.jpg"));
		
		driver.navigate().to("http://v.comicbus.com/online/comic-103.html?ch=471-5");
		img = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("TheImg")));
		System.out.println("Find : " + img.getAttribute("src"));
		FileUtils.copyURLToFile(new URL(img.getAttribute("src")), new File("005.jpg"));
		
		
		driver.close();
		driver.quit();
	}

}
