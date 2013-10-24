package mivc.System;

/**
 * An interface, considered part of the Command pattern.  It is used to 
 * govern the methods implemented in ConcreteCommand classes.
 * 
 * @author berlgeof
 */
public interface IUndoableCommand {

	/**
	 * "Undoes" the command, that is, state is returned to that which it
	 * was previous to executing the command.
	 */
	public void undo();
	
}
