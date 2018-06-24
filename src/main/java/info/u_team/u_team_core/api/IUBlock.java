package info.u_team.u_team_core.api;

import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.item.Item;

public interface IUBlock {
	
	public Item getItem();
	
	public UItemBlock getItemBlock();
	
	public String getName();
	
}
