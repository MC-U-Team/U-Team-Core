package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.registry.IUArrayRegistryType;
import net.minecraft.item.Item;

public class ToolSet implements IUArrayRegistryType {
	
	private final UItemAxe axe;
	private final UItemHoe hoe;
	private final UItemPickaxe pickaxe;
	private final UItemSpade spade;
	private final UItemSword sword;
	
	public ToolSet(UItemAxe axe, UItemHoe hoe, UItemPickaxe pickaxe, UItemSpade spade, UItemSword sword) {
		this.axe = axe;
		this.hoe = hoe;
		this.pickaxe = pickaxe;
		this.spade = spade;
		this.sword = sword;
	}
	
	@Override
	public Item[] getItemArray() {
		return new Item[] { axe, hoe, pickaxe, spade, sword };
	}
	
	public UItemAxe getAxe() {
		return axe;
	}
	
	public UItemHoe getHoe() {
		return hoe;
	}
	
	public UItemPickaxe getPickaxe() {
		return pickaxe;
	}
	
	public UItemSpade getSpade() {
		return spade;
	}
	
	public UItemSword getSword() {
		return sword;
	}
	
}
