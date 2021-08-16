package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.Collection;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import info.u_team.u_team_core.util.world.WorldUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DimensionTeleportSubCommand {

	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.dimteleport.success.";

	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("dimteleport") //
				.requires(source -> source.hasPermission(2)) //
				.then(Commands.argument("targets", EntityArgument.entities()) //
						.then(Commands.argument("dimension", DimensionArgument.dimension()) //
								.executes(context -> {
									return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimension(context, "dimension"));
								}) //
								.then(Commands.argument("location", Vec3Argument.vec3()) //
										.executes(context -> {
											return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimension(context, "dimension"), Vec3Argument.getVec3(context, "location"));
										}) //
										.then(Commands.argument("yaw", FloatArgumentType.floatArg(0, 360)) //
												.then(Commands.argument("pitch", FloatArgumentType.floatArg(-90, 90)) //
														.executes(context -> {
															return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimension(context, "dimension"), Vec3Argument.getVec3(context, "location"), FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"));
														}))))));
	}

	private static int execute(CommandSourceStack source, Collection<? extends Entity> targets, ServerLevel world) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, entity.position()));
		if (targets.size() == 1) {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "single", targets.iterator().next().getDisplayName(), world.dimension().location()), true);
		} else {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "multiple", targets.size(), world.dimension().location()), true);
		}
		return 0;
	}

	private static int execute(CommandSourceStack source, Collection<? extends Entity> targets, ServerLevel world, Vec3 pos) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, pos));
		sendPositionInfo(source, targets, world, pos);
		return 0;
	}

	private static int execute(CommandSourceStack source, Collection<? extends Entity> targets, ServerLevel world, Vec3 pos, float yaw, float pitch) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, pos.x(), pos.y(), pos.z(), yaw, pitch));
		sendPositionInfo(source, targets, world, pos);
		return 0;
	}

	private static void sendPositionInfo(CommandSourceStack source, Collection<? extends Entity> targets, ServerLevel world, Vec3 pos) {
		if (targets.size() == 1) {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "position.single", targets.iterator().next().getDisplayName(), world.dimension().location(), pos.x, pos.y, pos.z), true);
		} else {
			source.sendSuccess(new TranslatableComponent(SUCCESS_TRANSLATION_STRING + "position.multiple", targets.size(), world.dimension().location(), pos.x, pos.y, pos.z), true);
		}
	}
}
