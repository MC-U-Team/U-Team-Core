package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.item.Item;

public class ToolSet implements IUArrayRegistryType<Item> {
	
	private final UAxeItem axe;
	private final UHoeItem hoe;
	private final UPickaxeItem pickaxe;
	private final UShovelItem spade;
	private final USwordItem sword;
	
	public ToolSet(UAxeItem axe, UHoeItem hoe, UPickaxeItem pickaxe, UShovelItem spade, USwordItem sword) {
		this.axe = axe;
		this.hoe = hoe;
		this.pickaxe = pickaxe;
		this.spade = spade;
		this.sword = sword;
	}
	
	@Override
	public Item[] getArray() {
		return new Item[] { axe, hoe, pickaxe, spade, sword };
	}
	
	public UAxeItem getAxe() {
		return axe;
	}
	
	public UHoeItem getHoe() {
		return hoe;
	}
	
	public UPickaxeItem getPickaxe() {
		return pickaxe;
	}
	
	public UShovelItem getSpade() {
		return spade;
	}
	
	public USwordItem getSword() {
		return sword;
	}
	
}
