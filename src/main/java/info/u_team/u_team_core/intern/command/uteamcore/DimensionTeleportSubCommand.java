package info.u_team.u_team_core.intern.command.uteamcore;

import java.util.Collection;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import info.u_team.u_team_core.util.world.WorldUtil;
import net.minecraft.command.*;
import net.minecraft.command.arguments.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class DimensionTeleportSubCommand {
	
	private static final String SUCCESS_TRANSLATION_STRING = "commands.uteamcore.dimteleport.success.";
	
	public static ArgumentBuilder<CommandSource, ?> register() {
		return Commands.literal("dimteleport") //
				.requires(source -> source.hasPermissionLevel(2)) //
				.then(Commands.argument("targets", EntityArgument.entities()) //
						.then(Commands.argument("dimension", DimensionArgument.getDimension()) //
								.executes(context -> {
									return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimensionArgument(context, "dimension"));
								}) //
								.then(Commands.argument("location", Vec3Argument.vec3()) //
										.executes(context -> {
											return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimensionArgument(context, "dimension"), Vec3Argument.getVec3(context, "location"));
										}) //
										.then(Commands.argument("yaw", FloatArgumentType.floatArg(0, 360)) //
												.then(Commands.argument("pitch", FloatArgumentType.floatArg(-90, 90)) //
														.executes(context -> {
															return execute(context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimensionArgument(context, "dimension"), Vec3Argument.getVec3(context, "location"), FloatArgumentType.getFloat(context, "yaw"), FloatArgumentType.getFloat(context, "pitch"));
														}))))));
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, ServerWorld world) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, entity.getPositionVec()));
		if (targets.size() == 1) {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "single", targets.iterator().next().getDisplayName(), world.getDimensionKey().getLocation()), true);
		} else {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "multiple", targets.size(), world.getDimensionKey().getLocation()), true);
		}
		return 0;
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, ServerWorld world, Vector3d pos) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, pos));
		sendPositionInfo(source, targets, world, pos);
		return 0;
	}
	
	private static int execute(CommandSource source, Collection<? extends Entity> targets, ServerWorld world, Vector3d pos, float yaw, float pitch) {
		targets.forEach(entity -> WorldUtil.teleportEntity(entity, world, pos.getX(), pos.getY(), pos.getZ(), yaw, pitch));
		sendPositionInfo(source, targets, world, pos);
		return 0;
	}
	
	private static void sendPositionInfo(CommandSource source, Collection<? extends Entity> targets, ServerWorld world, Vector3d pos) {
		if (targets.size() == 1) {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "position.single", targets.iterator().next().getDisplayName(), world.getDimensionKey().getLocation(), pos.x, pos.y, pos.z), true);
		} else {
			source.sendFeedback(new TranslationTextComponent(SUCCESS_TRANSLATION_STRING + "position.multiple", targets.size(), world.getDimensionKey().getLocation(), pos.x, pos.y, pos.z), true);
		}
	}
}