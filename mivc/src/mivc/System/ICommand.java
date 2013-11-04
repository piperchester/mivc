package mivc.System;

/**
 * The main responsibility of this interface is for our command pattern. 
 * It is used to govern methods implemented in all concrete command classes.
 * 
 * @author berlgeof
 */
public interface ICommand {

	/**
	 * A method to carry out the command.
	 */
	public void execute();
	
}
