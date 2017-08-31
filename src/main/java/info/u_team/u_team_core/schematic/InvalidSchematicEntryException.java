package info.u_team.u_team_core.schematic;

public class InvalidSchematicEntryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidSchematicEntryException(String message) {
		super(message);
	}
	
	public InvalidSchematicEntryException() {
	}
	
}
