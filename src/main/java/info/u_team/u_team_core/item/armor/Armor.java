package info.u_team.u_team_core.item.armor;

public class Armor {
	
	private final UItemHelmet helmet;
	private final UItemChestplate chestplate;
	private final UItemLeggings leggings;
	private final UItemBoots boots;
	
	public Armor(UItemHelmet helmet, UItemChestplate chestplate, UItemLeggings leggings, UItemBoots boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	public UItemArmor[] getItemArray() {
		return new UItemArmor[] { helmet, chestplate, leggings, boots };
	}
	
	public UItemHelmet getHelmet() {
		return helmet;
	}
	
	public UItemChestplate getChestplate() {
		return chestplate;
	}
	
	public UItemLeggings getLeggings() {
		return leggings;
	}
	
	public UItemBoots getBoots() {
		return boots;
	}
}
