package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatter {

	public static String formatDate(Date d)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(d);

	}

	public static String formatDate(Calendar c)
	{
		return formatDate(c.getTime());
	}

	public static String formatDate(long l){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(l);
		return formatDate(c);
	}
	
	public static String formatDate()
	{
		return formatDate(Calendar.getInstance().getTime());
	}
}
