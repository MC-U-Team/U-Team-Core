package info.u_team.u_team_test.enchantment;

import java.util.List;

import info.u_team.u_team_core.enchantment.UEnchantment;
import net.minecraft.enchantment.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoSmeltEnchantment extends UEnchantment {
	
	public AutoSmeltEnchantment(String name) {
		super(name, Rarity.COMMON, EnchantmentType.DIGGER, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void on(HarvestDropsEvent event) {
		if (event.isSilkTouching()) {
			return;
		}
		PlayerEntity player = event.getHarvester();
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
			Inventory inventory = new Inventory(1);
			inventory.setInventorySlotContents(0, drop);
			
			final int index = i;
			
			world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world).ifPresent(recipe -> {
				ItemStack newDrop = recipe.getCraftingResult(inventory);
				if (newDrop != null && !newDrop.isEmpty()) {
					drops.set(index, newDrop);
					BlockPos pos = event.getPos();
					int amount = drop.getCount();
					recipe.getIcon();
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
						int split = ExperienceOrbEntity.getXPSplit(amount);
						amount -= split;
						world.addEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, split));
					}
				}
			});
		}
	}
}
