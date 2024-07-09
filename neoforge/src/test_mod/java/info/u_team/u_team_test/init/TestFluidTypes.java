package info.u_team.u_team_test.init;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TestFluidTypes {
	
	public static final CommonRegister<FluidType> FLUID_TYPES = CommonRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, TestMod.MODID);
	
	public static final RegistryEntry<FluidType> TEST_FLUID = FLUID_TYPES.register("test_fluid", () -> new FluidType(FluidType.Properties.create() //
			.descriptionId("block.uteamtest.test_fluid") //
			.lightLevel(5) //
			.fallDistanceModifier(0.1f) //
			.rarity(Rarity.RARE) //
			.viscosity(5) //
			.canSwim(true) //
			.canDrown(true) //
			.canExtinguish(true) //
			.canHydrate(true) //
			.supportsBoating(true) //
			.canConvertToSource(true) //
			.temperature(50) //
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL) //
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY) //
			.sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)) {
		
		@Override
		public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
			consumer.accept(new IClientFluidTypeExtensions() {
				
				private static final ResourceLocation STILL = new ResourceLocation("block/water_still");
				private static final ResourceLocation FLOW = new ResourceLocation("block/water_flow");
				
				@Override
				public ResourceLocation getStillTexture() {
					return STILL;
				}
				
				@Override
				public ResourceLocation getFlowingTexture() {
					return FLOW;
				}
				
				@Override
				public ResourceLocation getRenderOverlayTexture(Minecraft minecraft) {
					return new ResourceLocation("textures/misc/underwater.png");
				}
				
				@Override
				public int getTintColor() {
					return 0xFFE32F22;
				}
			});
		};
	});
	
	static void register() {
		FLUID_TYPES.register();
	}
	
}
