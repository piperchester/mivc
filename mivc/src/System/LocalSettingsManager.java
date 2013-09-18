/**
 * 
 */
package mivc.System;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author act4122
 *
 */
public class LocalSettingsManager {

	private HashMap<String, String>	m_strings = new HashMap<String, String>();
	private HashMap<String, Integer> m_ints = new HashMap<String, Integer>();
	
	
	/**
	 * constructor
	 * Loads settings from a file
	 * @param p_FileName - File Path to the settings file
	 */
	public LocalSettingsManager(String p_FileName)
	{
		if (p_FileName == "")
			return;
		
		// At this point we could use serialization...
		try
		{
			FileInputStream s_File = new FileInputStream(p_FileName);		
			Scanner s_Reader = new Scanner(s_File);
			
			String s_Tmp = "";
			while ((s_Tmp = s_Reader.next()) != "")
			{
				String[] s_Pair = s_Tmp.split(",,");
				
				if (s_Pair.length > 2)
					System.out.println(s_Pair[0] + " : " + s_Pair[1] + " : " + s_Pair[2]);
			}
			
			s_Reader.close();
			s_File.close();
			
		}
		catch (Exception ex)
		{
			// TODO: Handle errors
		}
	}
	
	/**
	 * Save
	 * Saves settings to a file
	 * @param p_FileName - File path to the settings file
	 * @return true if it succeeds, or false if it fails
	 */
	public boolean Save(String p_FileName)
	{
		try 
		{
			FileOutputStream s_File = new FileOutputStream(p_FileName);
			OutputStreamWriter s_Writer = new OutputStreamWriter(s_File);
		
			for (Integer i = 0; i < m_ints.size(); ++i)
			{
				// TODO: Finish implementing or revert to using serialization
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * getString
	 * Gets the string value to the key provided
	 * @param p_Key - Key to search by
	 * @return Value or null if not found
	 */
	public String getString(String p_Key)
	{
		return m_strings.get(p_Key);
	}
	
	/**
	 * getInt
	 * Gets the integer value to the key provided
	 * @param p_Key - Key to search by
	 * @return Value or null if not found
	 */
	public Integer getInt(String p_Key)
	{
		return m_ints.get(p_Key);
	}
	
	/**
	 * set
	 * Sets or creates the value in the key provided
	 * @param String p_Key - Key to store the value in
	 * @param String p_Value - Value to store
	 */
	public void set(String p_Key, String p_Value)
	{
		m_strings.put(p_Key, p_Value);
	}
	
	/**
	 * set
	 * Set or creates the value in the key provided
	 * @param String p_Key - Key to store the value in
	 * @param Integer p_Value - Value to store
	 */
	public void set(String p_Key, Integer p_Value)
	{
		m_ints.put(p_Key, p_Value);
	}
}
