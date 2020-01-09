package org.iit.mmp.tests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PAT004_Tests {
	WebDriver driver;
	@Test
	/*validateScheduleAppointmenttab is used to Schedule a doctor appointment.
	 * the navigateToAModule is to select the required link from the web page.
	 * the selectDoctor method is to select the required doctor.
	 * the selectApptDatenTime is to select the date and time of the appointment.
	 * the enterSymptoms method is to enter they symptoms of the illness. 
	 */
	

	public void validateScheduleAppointmenttab ()
	{
		
		navigateToAModule("Schedule Appointment");
		selectDoctor("Dr.Smith");
		selectApptDatenTime("15/March/2020","10Am");
		enterSymptoms("High Fever, Cough and Cold. ");
	}


	public void navigateToAModule(String modName) {
		driver.findElement(By.xpath("//span[contains(text(),'" + modName + "')]")).click();

	}

	public void selectDoctor(String drName) {
		
		//Find and click the Create new appointment button.
		driver.findElement(By.xpath("//div[@class='panel panel-cascade']//div//a//input")).click();
		
		//Find the list of available doctors
		List<WebElement> list = driver.findElements(By.xpath("//ul[@class='list-inline list-users']"));
		System.out.println("The size of the list is "+ list.size());
		for(int i=0;i<=list.size();i++) {
			String name=list.get(i).getText().toString();
			System.out.println("The values are: "+ name);
			if(name.contains(drName)) {
				String s = list.get(i).getText();
				System.out.println(s);
				// Click on the Book Appointment button for the selected doctor
				driver.findElement(By.xpath("//td["+(++i)+"]//button[1]")).click();
				break;
			}
		}
	}
	
	public void selectApptDatenTime(String doa, String time) {
		String date, month, year;
		String caldt, calmth, calyear;
		WebElement cal;

		// Split the date of appointment(doa) into a String Array
		String dateArray[] = doa.split("/");
		
		date = dateArray[0];
		month = dateArray[1];
		year = dateArray[2];
		
		
		driver.switchTo().frame(driver.findElement(By.id("myframe")));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
		
		driver.findElement(By.xpath(" //input[@id='datepicker']")).click();
		cal = driver.findElement(By.xpath("//div[@id='ui-datepicker-div']"));
		calyear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
		
		//To get the year from the calendar
		while(!calyear.equals(year)) {
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
			calyear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
			System.out.println(calyear);
		}
		// To get the month from the calendar
		calmth = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
		while(!calmth.equalsIgnoreCase(month)) {
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
			calmth = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
			System.out.println(calmth);
			}
		
		//To get the date from the calendar
		List<WebElement> rows, cols;
		rows =  cal.findElements(By.tagName("tr"));
		System.out.println(rows);
		for(int i=1;i<rows.size();i++) {
			cols= rows.get(i).findElements(By.tagName("td"));
			for(int j = 0;j < cols.size();j++) {
				caldt = cols.get(j).getText();
				 if(caldt.equals(date)) {
					 cols.get(j).click();
					 break;
				 }
				
			}

		}
		
		// To select the time of the appointment
		Select dropdown = new Select(driver.findElement(By.id("time")));
		dropdown.selectByVisibleText(time);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//button[@id='ChangeHeatName']")).click();
	}
	
	public void enterSymptoms(String symptoms) {
		
		driver.findElement(By.xpath("//textarea[@id='sym']")).sendKeys(symptoms);
		driver.findElement(By.xpath("//body/div[@class='site-holder']/div[@class='box-holder']/div[@class='content']/div[@class='row']/div[@class='col-md-6']/div[@class='panel panel-cascade']/div/input[1]")).click();
		
	}
}

	
