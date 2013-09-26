/**
 * 
 */
package mivc.System;

import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author act4122
 * LocalSettingsManager
 * This class will be able to hold and contain all of the settings, variables, 
 * switches that will be necessary for any project.
 */
public class LocalSettingsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<String, String>	m_strings = new HashMap<String, String>();
	public HashMap<String, Integer> m_ints = new HashMap<String, Integer>();
	public HashMap<String, Boolean> m_bools = new HashMap<String, Boolean>();
	
	/**
	 * constructor
	 * Loads settings from a file
	 * @param p_FileName - File Path to the settings file
	 */
	public LocalSettingsManager(String p_FileName)
	{
		if (p_FileName == "")
			return;
		
		try
		{
			FileInputStream s_Fis =
				new FileInputStream(p_FileName);
		
			ObjectInputStream s_Ois =
				new ObjectInputStream(s_Fis);
			
			LocalSettingsManager s_Tmp = 
					(LocalSettingsManager)s_Ois.readObject();
			
			// Cleanup
			s_Ois.close();
			s_Fis.close();
			
			this.m_ints = s_Tmp.m_ints;
			this.m_strings = s_Tmp.m_strings;
			this.m_bools = s_Tmp.m_bools;
			
			// gc should clean up that object
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public LocalSettingsManager()
	{
		// Do nothing, start a blank slate
	}
	
	/**
	 * Save
	 * Saves settings to a file
	 * @param p_FileName - File path to the settings file
	 * @return true if it succeeds, or false if it fails
	 */
	public boolean Save(String p_FileName)
	{
		if (p_FileName == "")
			return false;
		
		try
		{
			FileOutputStream s_Fos =
					new FileOutputStream(p_FileName);
			
			ObjectOutputStream s_Oos =
					new ObjectOutputStream(s_Fos);
			
			s_Oos.writeObject(this);
			s_Oos.close();
			s_Fos.close();
			
			System.out.println("Local Settings Manager saved to: " + p_FileName);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
		if (p_Key == "")
			return;
		
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
		if (p_Key == "")
			return;
		
		m_ints.put(p_Key, p_Value);
	}
	
	/**
	 * set
	 * Set or creates the value in the key provided
	 * @param String p_Key - Key to store the value in
	 * @param Boolean p_Value - Value to store
	 */
	public void set(String p_Key, Boolean p_Value)
	{
		if (p_Key == "")
			return;
		
		m_bools.put(p_Key, p_Value);
	}
}
