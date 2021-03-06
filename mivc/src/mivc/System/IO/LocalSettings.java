/**
 * 
 */
package mivc.System.IO;

import java.util.HashMap;
import java.util.Map;
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
 * Using the Singleton Pattern so it can be used across the entire subsystem
 * 
 * Usage:
 * LocalSettingsManager.getInstance().Load(Path to Settings File);
 * LocalSettingsManager.getInstance().set("Hello", "Hello");
 */
public class LocalSettings implements Serializable {

	private static LocalSettings m_instance = null;
	private static final long serialVersionUID = 1L;
	public static final String SETTINGS_PATH = "settings.prop";
	private Map<String, String>	m_strings = new HashMap<String, String>();
	private Map<String, Integer> m_ints = new HashMap<String, Integer>();
	private Map<String, Boolean> m_bools = new HashMap<String, Boolean>();
	
	/**
	 * LocalSettingsManager_Init
	 * Loads settings from a file
	 * @param p_FileName - File Path to the settings file
	 */
	public boolean Load(String p_FileName)
	{
		if (p_FileName == "")
			return false;
		
		try
		{
			FileInputStream s_Fis =
				new FileInputStream(p_FileName);
		
			ObjectInputStream s_Ois =
				new ObjectInputStream(s_Fis);
			
			LocalSettings s_Tmp = 
					(LocalSettings)s_Ois.readObject();
			
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
			System.err.println("No Settings Found... continuing with default");
			//ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	protected LocalSettings()
	{
		// Do nothing, start a blank slate
	}
	
	public static LocalSettings getInstance()
	{
		if (m_instance == null)
			m_instance = new LocalSettings();
		
		return m_instance;
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
	 * getBoolean
	 * Gets the boolean value to the key provided
	 * @param p_Key - Key to search by
	 * @return Value or false if not found
	 */
	public boolean getBoolean(String p_Key)
	{
		Boolean b = m_bools.get(p_Key);
		if (b != null && b) {
			return b;
		}
		return false;
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
