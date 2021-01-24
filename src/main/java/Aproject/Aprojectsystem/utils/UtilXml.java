package Aproject.Aprojectsystem.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilXml {
    public UtilXml() {
    }

    public XMLGregorianCalendar dataToCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }

    public Date calendarTodData(XMLGregorianCalendar xmlGregorianCalendar) throws DatatypeConfigurationException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a z");
        GregorianCalendar gCalendar = xmlGregorianCalendar.toGregorianCalendar();
        return gCalendar.getTime();
    }
}
