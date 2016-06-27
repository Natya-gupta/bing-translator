package TestNg;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class A {

	public static List<String> readText() {
	
		List<String> resultsUI= new ArrayList<String>();
		
		WebDriver driver= new FirefoxDriver();
		driver.get("https://www.bing.com/translator");
		//WebElement menu=driver.findElement(By.className("LS_HeaderTitle"));
		List<WebElement> list=driver.findElements(By.className("LS_HeaderTitle"));
		
		String line = "";  
	    try{
	    	 File file = new File("/home/natyagupta/workspace/TestNg1/src/Translator.csv");
	    	 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	  
	    	  while( (line = br.readLine())!= null ){
	    	        String [] tokens = line.split(",");
	    	        String from = tokens[0];
	    	        String to = tokens[1];
	    	        String word = tokens[2];
	    	        String s1 = "(//td[text()='"+from+"'])[1]";
	    	        String s2 = "(//td[text()='"+to+"'])[2]";
	    	        list.get(0).click();
	    	        Thread.sleep(2000);
	    	        driver.findElement(By.xpath(s1)).click();
	    	       
	    	        driver.findElement(By.className("srcTextarea")).sendKeys(word);
	    	       
	    	       list.get(1).click();
	    	        
	    	        driver.findElement(By.xpath(s2)).click();
	    	       Thread.sleep(2000);
	    	        String wb=driver.findElement(By.className("textArea")).getText();
	    	        resultsUI.add(wb);
	    	        driver.findElement(By.className("srcTextarea")).clear();
	    	        Thread.sleep(2000);
	    	  }  
	    	   
	    br.close();
	    }	
	    
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	   
		return resultsUI;
	}

}
