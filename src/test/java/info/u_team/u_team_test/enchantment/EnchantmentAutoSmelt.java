package info.u_team.u_team_test.enchantment;

import java.util.List;

import info.u_team.u_team_core.enchantment.UEnchantment;
import info.u_team.u_team_core.registry.util.CommonRegistry;
import net.minecraft.enchantment.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.VanillaRecipeTypes;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchantmentAutoSmelt extends UEnchantment {
	
	public EnchantmentAutoSmelt(String name) {
		super(name, Rarity.COMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
		CommonRegistry.registerEventHandler(this);
	}
	
	@SubscribeEvent
	public void on(HarvestDropsEvent event) {
		if (event.isSilkTouching()) {
			return;
		}
		EntityPlayer player = event.getHarvester();
		if (player == null) {
			return;
		}
		World world = player.getEntityWorld();
		if (world.isRemote) {
			return;
		}
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return;
		}
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if (level == 0) {
			return;
		}
		List<ItemStack> drops = event.getDrops();
		for (int i = 0; i < drops.size(); i++) {
			ItemStack drop = drops.get(i);
			InventoryBasic inventory = new InventoryBasic(new TextComponentString("drop"), 1);
			inventory.setInventorySlotContents(0, drop);
			FurnaceRecipe recipe = world.getRecipeManager().getRecipe(inventory, world, VanillaRecipeTypes.SMELTING);
			if (recipe != null) {
				ItemStack newDrop = recipe.getCraftingResult(inventory);
				if (newDrop != null && !newDrop.isEmpty()) {
					drops.set(i, newDrop);
					BlockPos pos = event.getPos();
					int amount = drop.getCount();
					float exp = recipe.getExperience();
					
					if (exp == 0.0F) {
						amount = 0;
					} else if (exp < 1.0F) {
						int calc = MathHelper.floor(amount * exp);
						
						if (calc < MathHelper.ceil(amount * exp) && (float) Math.random() < amount * exp - calc) {
							++calc;
						}
						amount = calc;
					}
					
					while (amount > 0) {
						int split = EntityXPOrb.getXPSplit(amount);
						amount -= split;
						world.spawnEntity(new EntityXPOrb(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, split));
					}
				}
			}
		}
	}
}
