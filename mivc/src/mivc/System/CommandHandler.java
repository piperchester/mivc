package mivc.System;

import java.util.Stack;


/**
 * The responsibility of this class is to act as an invoker in a 
 * command pattern.  This class handles the execution of the commands 
 * used throughout the system.  This class also enables undo and redo 
 * capabilities in our system by handling the logic that goes along 
 * with these functions.  There is currently no limit on the number of 
 * redo or undo operations.  As soon as a new operation is executed, 
 * the redo stack is cleared so redoing an old command is no longer 
 * possible until undo is used again.
 * 
 * @author Colin?
 * @author act4122
 */
public class CommandHandler {

	private Stack<IUndoableCommand> undoOperations;
	private Stack<IUndoableCommand> redoOperations;
	
	
	/**
	 * A no args constructor
	 */
	public CommandHandler() {

	}
	
	/**
	 * A method for adding commands that should be executed
	 * @param command the command to be executed
	 */
	public void addCommand(ICommand command) {
		// If this command is undoable, store if for possible undoing later.
		if (command instanceof IUndoableCommand) {
			if (undoOperations == null) {
				undoOperations = new Stack<IUndoableCommand>();
			}
			// Once a new command is pushed to undo we no longer need redos
			redoOperations = null;
			undoOperations.push((IUndoableCommand) command);
		} else {
			// If a command is run that is not undoable, clear the undo list
			undoOperations = null;
		}
		// Once a new command is executed we can empty the redo operations
		redoOperations = null;
		// Execute the command
		command.execute();
	}
	
	/**
	 * A method for undoing commands previously executed.  If none were 
	 * executed, nothing will happen.
	 */
	public void undo() {
		if (undoOperations == null) {
			return;
		}
		if (undoOperations.size() <= 0) {
			return;
		}
		if (redoOperations == null) {
			redoOperations = new Stack<IUndoableCommand>();
		}
		
		IUndoableCommand cmd = undoOperations.pop();
		redoOperations.push(cmd);
		cmd.undo();
	}
	
	/**
	 * A method for redoing a previously undone command.  If there are no
	 * commands available to redo then nothing will happen.
	 */
	public void redo() {
		if (redoOperations == null) {
			return;
		}
		if (redoOperations.size() <= 0) {
			return;
		}
		if (undoOperations == null) {
			undoOperations = new Stack<IUndoableCommand>();
		}
		
		IUndoableCommand cmd = redoOperations.pop();
		undoOperations.push(cmd);
		((ICommand)cmd).execute();
	}
}
