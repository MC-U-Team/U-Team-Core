package info.u_team.u_team_core.generation.schematic;

import java.net.URL;

import info.u_team.u_team_core.generation.IGeneration;
import info.u_team.u_team_core.schematic.USchematicRotation;

/**
 * Generation API<br>
 * -> Abstract Schematic Generation
 * 
 * @author HyCraftHD
 * @date 23.10.2017
 */
public abstract class AbstractGenerationSchematic implements IGeneration {
	
	protected URL url;
	protected boolean centerstart;
	protected USchematicRotation rotation;
	
	public AbstractGenerationSchematic(URL url) {
		this(url, false);
	}
	
	public AbstractGenerationSchematic(URL url, boolean centerstart) {
		this(url, centerstart, USchematicRotation.ROTATION_0);
	}
	
	public AbstractGenerationSchematic(URL url, boolean centerstart, USchematicRotation rotation) {
		this.url = url;
		this.centerstart = centerstart;
		this.rotation = rotation;
	}
	
}
