package info.u_team.u_team_test.init;

import java.util.Collection;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.*;
import com.mojang.brigadier.exceptions.*;

import info.u_team.u_team_core.dimension.UModDimension;
import info.u_team.u_team_core.registry.DimensionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.DimensionBasic;
import net.minecraft.command.*;
import net.minecraft.command.arguments.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.*;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.FORGE)
public class TestDimensions {
	
	public static final ModDimension basic = new UModDimension("basic", DimensionBasic::new);
	
	public static void construct() {
		DimensionRegistry.register(TestMod.modid, TestDimensions.class);
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void on(RegisterDimensionsEvent event) {
		if (!DimensionManager.getRegistry().containsKey(basic.getRegistryName())) { // How do we know when the dimension needs to be registered??
			DimensionManager.registerDimension(basic.getRegistryName(), basic, null, true);
		}
	}
	
	@SubscribeEvent
	public static void on(FMLServerStartingEvent event) {
		event.getCommandDispatcher().register(LiteralArgumentBuilder.<CommandSource> literal("test").then(Teleport.register()));
	}
	
	public static class Teleport {
		
		private static final SimpleCommandExceptionType NO_ENTITIES = new SimpleCommandExceptionType(new TranslationTextComponent("commands.forge.setdim.invalid.entity"));
		
		static ArgumentBuilder<CommandSource, ?> register() {
			return Commands.literal("teleport").requires(cs -> cs.hasPermissionLevel(2)) // permission
					.then(Commands.argument("dim", StringArgumentType.string())
					.then(Commands.argument("targets", EntityArgument.entities())
					.executes(ctx -> execute(ctx.getSource(), EntityArgument.getEntitiesAllowingNone(ctx, "targets"), StringArgumentType.getString(ctx, "dim")))));
		}
		
		private static int execute(CommandSource sender, Collection<? extends Entity> entities, String dimS) throws CommandSyntaxException {
			System.out.println("TEST");
			if(dimS.equals("basic")) {
				dimS = "uteamtest:" + dimS;
			} else {
				dimS = "minecraft:" + dimS;
			}
			DimensionType dim = DimensionType.byName(ResourceLocation.tryCreate(dimS));
			if (entities.isEmpty())
				throw NO_ENTITIES.create();
			
			// final ITeleporter teleporter = new CommandTeleporter(pos);
			entities.stream().filter(e -> e.dimension == dim).forEach(e -> sender.sendFeedback(new TranslationTextComponent("commands.forge.setdim.invalid.nochange", e.getDisplayName(), dim), true));
			entities.stream().filter(e -> e.dimension != dim).forEach(e -> e.changeDimension(dim));
			
			final PlayerEntity test;
			
			return 0;
		}
		
	}
}
