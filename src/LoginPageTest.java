import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest {
	
		WebDriver driver;
		String query = "select * from userpasstable";
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		
		String deleteQuery = "DELETE from userpasstable";
		String insertQuery = "INSERT INTO userpasstable (username, password) VALUES ('admin123@yahoo.com', 'admin123')";
		String insertQuery1 = "INSERT INTO userpasstable (username, password) VALUES ('admin456@yahoo.com', 'admin456')";
		String insertQuery2 = "INSERT INTO userpasstable (username, password) VALUES ('admin789@yahoo.com', 'admin789')";
		String insertQuery3 = "INSERT INTO userpasstable (username, password) VALUES ('admin000@yahoo.com', 'admin000')";
		String insertQuery4 = "INSERT INTO userpasstable (username, password) VALUES ('admin007@yahoo.com', 'admin007')";
		
		
		@Test(priority = 1)
		public void deleteOperation() throws SQLException {
			int numberRow = 5;
			stmt.executeUpdate(deleteQuery);
			ResultSet rs = stmt.executeQuery("select count(*) from userpasstable");
			  while(rs.next()){
			        numberRow = rs.getInt("count(*)");
			    }
			Assert.assertEquals(numberRow, 0);
			  
		}
		
  @Test(priority = 2)
  public void CreateOperationTest() throws SQLException {
  
//	  driver.get("https://www.saucedemo.com/");
		stmt.executeUpdate(this.insertQuery);  
		stmt.executeUpdate(this.insertQuery1);  
		stmt.executeUpdate(this.insertQuery2);  
		stmt.executeUpdate(this.insertQuery3);  
		stmt.executeUpdate(this.insertQuery4);  
		int numberRow = 0;
		
		ResultSet rs = stmt.executeQuery("select count(*) from userpasstable");
		  while(rs.next()){
		        numberRow = rs.getInt("count(*)");
		    }
		Assert.assertEquals(numberRow, 5);
	
		
  }
  
  // read test
  @Test(priority = 3)
  public void LoginTest() throws SQLException, InterruptedException {
	
	  rs = stmt.executeQuery(query);
	  while(rs.next()) {
	
	  this.invokeBrowser();
	  driver.get("https://www.demoblaze.com/index.html");
	  driver.findElement(By.xpath("//a[text() = 'Log in']")).click();
	  
	 
	  		
	  driver.findElement(By.id("loginusername")).sendKeys(rs.getString("username"));
	  Thread.sleep(2000);
	  driver.findElement(By.id("loginpassword")).sendKeys(rs.getString("password"));
	  Thread.sleep(2000);
	  driver.findElement(By.xpath("//button[text()='Log in']")).click();
	
	  driver.close();
	  
	  }	
	  
  }
  
  public void invokeBrowser() {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\vishw\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

  }
  
  	@BeforeMethod
  	public void beforeMethod() throws SQLException {
  	
  		try {
  		
  			// Step 1: connection to the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost/saucedatabase", "root", "");
			System.out.println("Connection is created");
			
			// Step 2: Prepare a statement
			stmt = conn.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
 	}

  	@AfterMethod
  	public void afterMethod() throws SQLException {
  
  		rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			System.out.print(rs.getString("username"));
			System.out.println(" --- " + rs.getString("password"));
			
		}
		
		
		if(rs != null) {
			rs.close();
		}
		if(stmt != null) {
			stmt.close();
		}
		if(conn != null) {
			conn.close();
			System.out.println("Connection closed");
			}
  		}
  
  	}

