package info.u_team.u_team_core.item.tool;

import com.google.common.collect.Multimap;

import info.u_team.u_team_core.api.*;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.*;

public class UItemSword extends ItemSword implements IUItem, IModelProvider {
	
	protected String name;
	
	protected double speedmodifier;
	
	public UItemSword(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemSword(String name, UCreativeTab tab, ToolMaterial material) {
		this(name, tab, material, -2.4000000953674316D);
	}
	
	public UItemSword(String name, ToolMaterial material, double speedmodifier) {
		this(name, null, material, speedmodifier);
	}
	
	public UItemSword(String name, UCreativeTab tab, ToolMaterial material, double speedmodifier) {
		super(material);
		
		this.name = name;
		this.speedmodifier = speedmodifier;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speedmodifier, 0));
		}
		
		return multimap;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(this, 0, getRegistryName());
	}
}
