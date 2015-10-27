package util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XWSDate {
	public static XMLGregorianCalendar getCurrentDate() {
		XMLGregorianCalendar retVal=null;
		
		GregorianCalendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int timezone = cal.get(Calendar.ZONE_OFFSET)/60000;
		
		try {
			retVal=DatatypeFactory.newInstance().newXMLGregorianCalendarDate(year, month, day, timezone);
		} catch (DatatypeConfigurationException e) {
			System.err.println("Error creating calendar for the current date.");
		}
		return retVal;
	}
}
