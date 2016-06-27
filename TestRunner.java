package TestNg;




import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestRunner {
	static List<String> a=null;
	static List<String> b=null;
	
	@BeforeTest
	public void get()
	{
	
	 a=A.readText();
     try {
		b=ExampleTest.getText();
	} catch (IOException e) {
		
		e.printStackTrace();
	} catch (ParseException e) {
		
		e.printStackTrace();
	}	
  }
	
	@DataProvider(name="objects")
	public static Object[][] getObjects(){
		
		Object[][] objects= new Object[a.size()][2];
		for(int i=0;i<a.size();i++){
			objects[i][0]= a.get(i);
			objects[i][1]=b.get(i);
		}
		return objects;
	}
	
@Test(dataProvider="objects")
public void check(String first,String second)
{
	System.out.println(first);
	System.out.println(second);
	Assert.assertEquals(first,second);
}
}
	

