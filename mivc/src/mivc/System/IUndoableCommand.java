package mivc.System;

/**
 * The main responsibility of this interface is for our command pattern. 
 * It is used to govern methods implemented in any concrete command classes 
 * that are intended to support undo.
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
