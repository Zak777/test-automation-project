package ua.edu.sumdu.ta.roman.lab;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebSiteLabTest {
	private WebDriver driver;
	private String userName = "newuser12";
	private String password = "Ff@12345";
	
	public void login() {
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.name("j_username")).sendKeys(userName);
		driver.findElement(By.name("j_password")).sendKeys(password);
		driver.findElement(By.name("submit")).click();
	}
	
	public void switchWindow(String windowTitle) throws Exception {
		Thread.sleep(7000);
		Set<String> windows = driver.getWindowHandles();
		for (String handle : windows) {
			driver.switchTo().window(handle);
			if (driver.getTitle().contains(windowTitle)) {
				return;
			}
		}
	}
	
	public void goToBuilding() {
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Building")).click();
	}
	
	public void goToRack() {
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Building")).click();
		driver.findElement(By.linkText("Floor#3")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
	}
	
	public void goToRackNew() {
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Building")).click();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
	}
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
	}
	
	@Test
	public void test01() throws Exception {
		String expected = "registerForm:j_idt26";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.linkText("Registration")).click();
		String actual = new String(driver.findElement(By.className("button")).getAttribute("name"));
		assertEquals(expected, actual);
	}

	@Test
	public void test02() throws Exception {
		String expected = "Login must not be empty.";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.linkText("Registration")).click();
		driver.findElement(By.xpath("//input[@name='registerForm:j_idt26']")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='registerForm']/table/tbody/tr[1]/td[3]/span")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test03() throws Exception {
		String expected = "Login must be alphanumeric string with length => 6 and <= 50.";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.linkText("Registration")).click();
		driver.findElement(By.name("registerForm:username")).sendKeys("newuser#1");
		driver.findElement(By.name("registerForm:password")).sendKeys("Ff@12345");
		driver.findElement(By.name("registerForm:confirmPassword")).sendKeys("Ff@12345");
		driver.findElement(By.name("registerForm:email")).sendKeys("some@email.com");
		Select role = new Select(driver.findElement(By.name("registerForm:role")));
		role.selectByVisibleText("Read / Write");
		driver.findElement(By.xpath("//input[@name='registerForm:j_idt26']")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='registerForm']/table/tbody/tr[1]/td[3]/span")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test04() throws Exception {
		String expected = "You have successfully registered\nUse your credentials to login";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.linkText("Registration")).click();
		driver.findElement(By.name("registerForm:username")).sendKeys(userName);
		driver.findElement(By.name("registerForm:password")).sendKeys(password);
		driver.findElement(By.name("registerForm:confirmPassword")).sendKeys("Ff@12345");
		driver.findElement(By.name("registerForm:email")).sendKeys("some@email.com");
		Select role = new Select(driver.findElement(By.name("registerForm:role")));
		role.selectByVisibleText("Read / Write");
		driver.findElement(By.xpath("//input[@name='registerForm:j_idt26']")).click();
		String actual = new String(driver.findElement(By.xpath("//div[@class='justRegisteredBlock']")).getText());
		assertEquals(expected, actual);
	}
		
	@Test
	public void test05() throws Exception {
		String expected = "Login";
		driver.get("http://s1.web.sumdu.edu.ua:8080/login.jsp");
		String actual = new String(driver.findElement(By.name("submit")).getAttribute("value"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void test06() throws Exception {
		String expected = "Your login attempt was not successful, try again.\nCaused : Username/Password entered is incorrect.";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.name("j_username")).sendKeys("newuser#7");
		driver.findElement(By.name("j_password")).sendKeys("Ff@12345");
		driver.findElement(By.name("submit")).click();
		String actual = new String(driver.findElement(By.xpath("//div[@class='errorblock']")).getText());
		assertEquals(expected, actual);
	}

	@Test
	public void test07() throws Exception {
		String expected = "Your login attempt was not successful, try again.\nCaused : Username/Password entered is incorrect.";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.name("submit")).click();
		String actual = new String(driver.findElement(By.xpath("//div[@class='errorblock']")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test08() throws Exception {
		String expected = "Your login attempt was not successful, try again.\nCaused : Username/Password entered is incorrect.";
		driver.get("http://s1.web.sumdu.edu.ua:8080");
		driver.findElement(By.name("j_username")).sendKeys("newuser7");
		driver.findElement(By.name("j_password")).sendKeys("Ff@1234");
		driver.findElement(By.name("submit")).click();
		String actual = new String(driver.findElement(By.xpath("//div[@class='errorblock']")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test09() throws Exception {
		String expected = "Log out";
		login();
		String actual = new String(driver.findElement(By.className("logout")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test10() throws Exception {
		String expected = "[Strakhov_Roman]Country";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Country");
		driver.findElement(By.name("j_idt74:language")).sendKeys("eng");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test11() throws Exception {
		String expected = "Create city";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		String actual = new String(driver.findElement(By.className("button")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test12() throws Exception {
		String expected = "[Strakhov_Roman]City";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("Create city")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]City");
		driver.findElement(By.name("j_idt74:population")).sendKeys("300");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test13() throws Exception {
		String expected = "Create building";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		String actual = new String(driver.findElement(By.linkText("Create building")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test14() throws Exception {
		String expected = "[Strakhov_Roman]Building";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		driver.findElement(By.linkText("Create building")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Building");
		driver.findElement(By.name("j_idt74:streetName")).sendKeys("Some name");
		driver.findElement(By.name("j_idt74:number")).sendKeys("44");
		driver.findElement(By.name("j_idt74:square")).sendKeys("2");
		Select isConnected = new Select(driver.findElement(By.name("j_idt74:isconnected")));
		isConnected.selectByVisibleText("Lit");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test15() throws Exception {
		String expected = "Create floor";
		goToBuilding();
		String actual = new String(driver.findElement(By.linkText("Create floor")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test16() throws Exception {
		String expected = "[Strakhov_Roman]Floor";
		goToBuilding();
		driver.findElement(By.linkText("Create floor")).click();
		driver.findElement(By.name("j_idt74:number")).sendKeys("3");
		driver.findElement(By.name("j_idt74:square")).sendKeys("1");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test17() throws Exception {
		String expected = "Create room";
		goToBuilding();
		driver.findElement(By.linkText("Floor#3")).click();
		String actual = new String(driver.findElement(By.linkText("Create room")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test18() throws Exception {
		String expected = "[Strakhov_Roman]Room";
		goToBuilding();
		driver.findElement(By.linkText("Floor#3")).click();
		driver.findElement(By.linkText("Create room")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Room");
		driver.findElement(By.name("j_idt74:square")).sendKeys("1");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test19() throws Exception {
		String expected = "Create rack";
		goToBuilding();
		driver.findElement(By.linkText("Floor#3")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		String actual = new String(driver.findElement(By.linkText("Create rack")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test20() throws Exception {
		String expected = "[Strakhov_Roman]Rack";
		goToBuilding();
		driver.findElement(By.linkText("Floor#3")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("Create rack")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Rack");
		driver.findElement(By.name("j_idt74:width")).sendKeys("10");
		driver.findElement(By.name("j_idt74:length")).sendKeys("7");
		driver.findElement(By.name("j_idt74:height")).sendKeys("5");
		Select physicalStatus = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		physicalStatus.selectByVisibleText("Planned");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test21() throws Exception {
		String expected = "Create device";
		goToRack();
		String actual = new String(driver.findElement(By.linkText("Create device")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test22() throws Exception {
		String expected = "[Strakhov_Roman]Device";
		goToRack();
		driver.findElement(By.linkText("Create device")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Device");
		driver.findElement(By.name("j_idt74:macAddress")).sendKeys("FF:34:5A:11:27:3C");
		driver.findElement(By.name("j_idt74:ram")).sendKeys("4");
		driver.findElement(By.name("j_idt74:cpu")).sendKeys("2");
		driver.findElement(By.name("j_idt74:ipAddress")).sendKeys("192.168.0.1");
		Select physicalStatus = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		physicalStatus.selectByVisibleText("Planned");
		driver.findElement(By.name("j_idt74:width")).sendKeys("5");
		driver.findElement(By.name("j_idt74:length")).sendKeys("7");
		driver.findElement(By.name("j_idt74:height")).sendKeys("10");
		driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[10]/td/a[1]")).click();
		switchWindow("Navigation Tree");
		driver.findElement(By.linkText("Country: [Strakhov_Roman]Country")).click();
		driver.findElement(By.id("OK")).click();
		switchWindow("Create device");
		driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[11]/td/a[1]")).click();
		switchWindow("Navigation Tree");
		driver.findElement(By.linkText("Country: [Strakhov_Roman]Country")).click();
		driver.findElement(By.id("OK")).click();
		switchWindow("Create device");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test23() throws Exception {
		String expected = "[Strakhov_Roman]POS Terminal";
		goToRack();
		driver.findElement(By.linkText("Post Terminal (s)")).click();
		driver.findElement(By.linkText("Create Post Terminal")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]POS Terminal");
		driver.findElement(By.name("j_idt74:width")).sendKeys("15");
		driver.findElement(By.name("j_idt74:length")).sendKeys("14");
		driver.findElement(By.name("j_idt74:height")).sendKeys("13");
		Select physicalStatus = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		physicalStatus.selectByVisibleText("Planned");
		driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[6]/td/a[1]")).click();
		switchWindow("Navigation Tree");
		driver.findElement(By.linkText("Country: [Strakhov_Roman]Country")).click();
		driver.findElement(By.id("OK")).click();
		switchWindow("Create posterm");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test24() throws Exception {
		String expected = "[Strakhov_Roman]Pay Box";
		goToRack();
		driver.findElement(By.linkText("Pay Box (s)")).click();
		driver.findElement(By.linkText("Create Pay Box")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Pay Box");
		driver.findElement(By.name("j_idt74:secureProtocol")).sendKeys("ssh");
		driver.findElement(By.name("j_idt74:displaySize")).sendKeys("11");
		Select physicalStatus = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		physicalStatus.selectByVisibleText("Planned");
		driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[5]/td/a[1]")).click();
		switchWindow("Navigation Tree");
		driver.findElement(By.linkText("Country: [Strakhov_Roman]Country")).click();
		driver.findElement(By.id("OK")).click();
		switchWindow("Create paybox");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test25() throws Exception {
		String expected = "[Strakhov_Roman]ATM";
		goToRack();
		driver.findElement(By.linkText("ATM (s)")).click();
		driver.findElement(By.linkText("Create ATM")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]ATM");
		driver.findElement(By.name("j_idt74:connectionType")).sendKeys("https");
		driver.findElement(By.name("j_idt74:extraSecurity")).sendKeys("Yes");
		Select physicalStatus = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		physicalStatus.selectByVisibleText("Planned");
		driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[5]/td/a[1]")).click();
		switchWindow("Navigation Tree");
		driver.findElement(By.linkText("Country: [Strakhov_Roman]Country")).click();
		driver.findElement(By.id("OK")).click();
		switchWindow("Create atm");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test26() throws Exception {
		String expected = "Create city";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		String actual = new String(driver.findElement(By.className("button")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test27() throws Exception {
		String expected = "Country";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		Select continent = new Select(driver.findElement(By.name("j_idt74:continent")));
		continent.selectByVisibleText("Africa");
		driver.findElement(By.name("j_idt74:language")).clear();
		driver.findElement(By.name("j_idt74:language")).sendKeys("ua");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test28() throws Exception {
		String[] expected = {"[Strakhov_Roman]Country", "Africa", "ua"};
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("Parameters")).click();
		String[] actual = new String[3];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test29() throws Exception {
		String[] expected = {"City", "[Strakhov_Roman]City", "250", "No"};
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:population")).clear();
		driver.findElement(By.name("j_idt74:population")).sendKeys("250");
		Select isRegionalCenter = new Select(driver.findElement(By.name("j_idt74:isRegionalCenter")));
		isRegionalCenter.selectByVisibleText("No");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[4];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test30() throws Exception {
		String[] expected = {"Building", "[Strakhov_Roman]Building", "Supruna", "11", "200", "Unlit"};
		goToBuilding();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:streetName")).clear();
		driver.findElement(By.name("j_idt74:streetName")).sendKeys("Supruna");
		driver.findElement(By.name("j_idt74:number")).clear();
		driver.findElement(By.name("j_idt74:number")).sendKeys("11");
		driver.findElement(By.name("j_idt74:square")).clear();
		driver.findElement(By.name("j_idt74:square")).sendKeys("200");
		Select isConnected = new Select(driver.findElement(By.name("j_idt74:isconnected")));
		isConnected.selectByVisibleText("Unlit");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[6];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		actual[5] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[10]/td")).getText();		
		assertArrayEquals(expected, actual);
	}
	
	//Fail
	@Test
	public void test31() throws Exception {
		String[] expected = {"Floor", "[Strakhov_Roman]Floor", "4", "50"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#3")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:number")).clear();
		driver.findElement(By.name("j_idt74:number")).sendKeys("4");
		driver.findElement(By.name("j_idt74:square")).clear();
		driver.findElement(By.name("j_idt74:square")).sendKeys("50");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[4];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test32() throws Exception {
		String[] expected = {"Room", "[Strakhov_Roman]Room", "5"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:square")).clear();
		driver.findElement(By.name("j_idt74:square")).sendKeys("5");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[3];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test33() throws Exception {
		String[] expected = {"Rack", "[Strakhov_Roman]Rack", "11", "9", "8", "In Service"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:width")).clear();
		driver.findElement(By.name("j_idt74:width")).sendKeys("11");
		driver.findElement(By.name("j_idt74:length")).clear();
		driver.findElement(By.name("j_idt74:length")).sendKeys("9");
		driver.findElement(By.name("j_idt74:height")).clear();
		driver.findElement(By.name("j_idt74:height")).sendKeys("8");
		Select status = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		status.selectByVisibleText("In Service");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[6];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		actual[5] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[10]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test34() throws Exception {
		String[] expected = {"Device", "[Strakhov_Roman]Device", "CC:34:5A:11:27:3C", "8", "4", "10.10.10.11", "In Service", "7", "9", "11"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Device")).click();
		driver.findElement(By.linkText("Parameters")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:macAddress")).clear();
		driver.findElement(By.name("j_idt74:macAddress")).sendKeys("CC:34:5A:11:27:3C");
		driver.findElement(By.name("j_idt74:ram")).clear();
		driver.findElement(By.name("j_idt74:ram")).sendKeys("8");
		driver.findElement(By.name("j_idt74:cpu")).clear();
		driver.findElement(By.name("j_idt74:cpu")).sendKeys("4");
		driver.findElement(By.name("j_idt74:ipAddress")).clear();
		driver.findElement(By.name("j_idt74:ipAddress")).sendKeys("10.10.10.11");
		Select status = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		status.selectByVisibleText("In Service");
		driver.findElement(By.name("j_idt74:width")).clear();
		driver.findElement(By.name("j_idt74:width")).sendKeys("7");
		driver.findElement(By.name("j_idt74:length")).clear();
		driver.findElement(By.name("j_idt74:length")).sendKeys("9");
		driver.findElement(By.name("j_idt74:height")).clear();
		driver.findElement(By.name("j_idt74:height")).sendKeys("11");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[10];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		actual[5] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[10]/td")).getText();
		actual[6] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[11]/td")).getText();
		actual[7] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[12]/td")).getText();
		actual[8] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[13]/td")).getText();
		actual[9] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[14]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	//Fail
	@Test
	public void test35() throws Exception {
		String[] expected = {"POS Terminal", "[Strakhov_Roman]POS Terminal", "20", "19", "18", "In Service"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
		driver.findElement(By.linkText("Post Terminal (s)")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]POS Terminal")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:width")).clear();
		driver.findElement(By.name("j_idt74:width")).sendKeys("20");
		driver.findElement(By.name("j_idt74:length")).clear();
		driver.findElement(By.name("j_idt74:length")).sendKeys("19");
		driver.findElement(By.name("j_idt74:height")).clear();
		driver.findElement(By.name("j_idt74:height")).sendKeys("18");
		Select status = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		status.selectByVisibleText("In Service");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[6];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		actual[5] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[10]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test36() throws Exception {
		String[] expected = {"Pay Box", "[Strakhov_Roman]Pay Box", "https", "15", "In Service"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
		driver.findElement(By.linkText("Pay Box (s)")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Pay Box")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:secureProtocol")).clear();
		driver.findElement(By.name("j_idt74:secureProtocol")).sendKeys("https");
		driver.findElement(By.name("j_idt74:displaySize")).clear();
		driver.findElement(By.name("j_idt74:displaySize")).sendKeys("15");
		Select status = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		status.selectByVisibleText("In Service");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[5];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test37() throws Exception {
		String[] expected = {"ATM", "[Strakhov_Roman]ATM", "ssh", "No", "In Service"};
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Rack")).click();
		driver.findElement(By.linkText("ATM (s)")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]ATM")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("j_idt74:connectionType")).clear();
		driver.findElement(By.name("j_idt74:connectionType")).sendKeys("ssh");
		driver.findElement(By.name("j_idt74:extraSecurity")).clear();
		driver.findElement(By.name("j_idt74:extraSecurity")).sendKeys("No");
		Select status = new Select(driver.findElement(By.name("j_idt74:physicalStatus")));
		status.selectByVisibleText("In Service");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String[] actual = new String[5];
		actual[0] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[2]/td")).getText();
		actual[1] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td")).getText();
		actual[2] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[7]/td")).getText();
		actual[3] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[8]/td")).getText();
		actual[4] = driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[9]/td")).getText();
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test38() throws Exception {
		String expected = "[Strakhov_Roman]ATM";
		goToRackNew();
		driver.findElement(By.linkText("ATM (s)")).click();
		String actual = new String(driver.findElement(By.linkText("[Strakhov_Roman]ATM")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test39() throws Exception {
		String expected = "No records found.";
		goToRackNew();
		driver.findElement(By.linkText("ATM (s)")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt128:0:j_idt130")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt126")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt128_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test40() throws Exception {
		String expected = "No records found.";
		goToRackNew();
		driver.findElement(By.linkText("Pay Box (s)")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt143:0:j_idt145")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt141")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt143_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test41() throws Exception {
		String expected = "No records found.";
		goToRackNew();
		driver.findElement(By.linkText("Post Terminal (s)")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt158:0:j_idt160")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt156")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt158_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test42() throws Exception {
		String expected = "No records found.";
		goToRackNew();
		driver.findElement(By.name("j_idt76:tabView:j_idt113:0:j_idt115")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt111")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt113_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test43() throws Exception {
		String expected = "No records found.";
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Room")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt107:0:j_idt109")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt105")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt107_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test44() throws Exception {
		String expected = "No records found.";
		goToBuilding();
		driver.findElement(By.linkText("Floor#4")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt109:0:j_idt111")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt107")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt109_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test45() throws Exception {
		String expected = "No records found.";
		goToBuilding();
		driver.findElement(By.name("j_idt76:tabView:j_idt113:0:j_idt115")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt111")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt113_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test46() throws Exception {
		String expected = "Create building";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]City")).click();
		String actual = new String(driver.findElement(By.linkText("Create building")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test47() throws Exception {
		String expected = "No records found.";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.linkText("[Strakhov_Roman]Country")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt109:0:j_idt111")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt107")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt109_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test48() throws Exception {
		String expected = "Create country";
		login();
		driver.findElement(By.className("button")).click();
		String actual = new String(driver.findElement(By.linkText("Create country")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test49() throws Exception {
		String expected = "Country with such name already exists.";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.name("j_idt74:name")).sendKeys("[Strakhov_Roman]Country");
		driver.findElement(By.name("j_idt74:language")).sendKeys("eng");
		driver.findElement(By.name("j_idt74:j_idt76")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='table_data']/table/tbody/tr[1]/td/span")).getText());
		assertEquals(expected, actual);
	}
	
	@Test
	public void test50() throws Exception {
		String expected = "No records found.";
		login();
		driver.findElement(By.className("button")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt89:0:j_idt91")).click();
		driver.findElement(By.name("j_idt76:tabView:j_idt87")).click();
		String actual = new String(driver.findElement(By.xpath("//*[@id='j_idt76:tabView:j_idt89_data']/tr/td")).getText());
		assertEquals(expected, actual);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}