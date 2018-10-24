package com.tcm.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TCM {
    public WebDriver driver;
    public Set<String> titles = new HashSet<String>();
	@Before
	public void setup(){
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.tcm.com/tcmdb/");
		driver.manage().window().maximize();
		
		//Taking the list of top 5 links
		String topTenMovieSection = "//div[@class='sectionWrap']";
		waitToVisible(topTenMovieSection);			
	}
	
	@Test
	public void movieSearch(){
		//Taking the list of movie searches
		List<WebElement> movies = driver.findElements(By.xpath("//div[@id='movieSearchList']/div/a"));
		int size = movies.size();
		int j=0;
		for(int i=1;i<=5;i++){
			
			WebElement element = driver.findElement(By.xpath("//div[@id='movieSearchList']/div/a["+i+"]"));
			String title = element.getText();
			element.click();
			String pageTitle = driver.getTitle();
			
			//Waiting for page to be displayed properly
			String menuLinks = "//h3[text()='MAIN Links']";
			waitToVisible(menuLinks);
			
			//Verifying the movie title
			String titleOnLoadedPage = driver.findElement(By.xpath("//div[@id='dbartimgtitle']//span[@class='db-movietitle']")).getText();
			//Assert.assertEquals("Title are matching", pageTitle, titleOnLoadedPage);
			System.out.println(titleOnLoadedPage);
			
			//Printing the release year
			String releasedYearInString = driver.findElement(By.xpath("//div[@id='dbartimgtitle']//span[@class='dbyear']")).getText().replaceAll("[()]", " ");
			//int releasedYear = Integer.parseInt(releasedYearInString);			
			System.out.println(releasedYearInString);
			
			//Clicking on overview 
			String overview = "//h3[text()='MAIN Links']/..//a[@title='Overview']";
			waitToClick(overview);
			driver.findElement(By.xpath(overview)).click();
			
			String castAndCrew="//h3[text()='Cast & Crew']";
			waitToVisible(castAndCrew);
			
			//Printing Actor Names
			List<WebElement> actors = driver.findElements(By.xpath("//h3[text()='Cast & Crew']/..//div[@class='castandcrew-cont'][1]/div[2]//span"));
			int numberOfActors = actors.size();
			for(int z=1;z<=actors.size();z++){
				String namesOfActors = driver.findElement(By.xpath("//h3[text()='Cast & Crew']/..//div[@class='castandcrew-cont'][1]/div[2]//div["+z+"]//span")).getText();
				System.out.println(namesOfActors);
			}
			driver.navigate().back();
		
			
			
			
		}
		
	}	
	@After
	public void end(){
		driver.quit();
	}
	
	public void waitToClick(String locator){
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}
	
	public void waitToVisible(String locator){
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}
}
