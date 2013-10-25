package mivc.System;

/**
 * An interface, considered part of the Command pattern.  It is used to 
 * govern the methods implemented in ConcreteCommand classes.
 * 
 * @author berlgeof
 */
public interface ICommand {

	/**
	 * Carries out the command.
	 */
	public void execute();
	
}