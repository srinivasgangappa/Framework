package generic;


import java.io.FileInputStream;
import java.util.*;

public class Propertie
{
	public static String getPropertieValue(String filePath, String key) 
	{
		String value="";
		Properties ppt = new Properties(); 
		
		try
		{
		ppt.load(new FileInputStream(filePath));
		value=ppt.getProperty(key);
		}
		catch(Exception e)
		{
			
		}
		
		return value;
		
	}

}
