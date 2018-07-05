package info.u_team.u_team_core.api;

/**
 * Used for metadata items and blocks
 * 
 * @author HyCraftHD
 * @date 26.06.2018
 */
public interface IMetaType extends IPropertyList {
	
	@Override
	public String getName();
	
	public int getMetadata();
	
}
