package info.u_team.u_team_core.intern.command;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.schematic.*;
import info.u_team.u_team_core.util.io.FileUtil;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandUSchematic extends CommandBase {
	
	private String lang = "command.uschematic.";
	
	@Override
	public String getName() {
		return "uschematic";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {
		return lang + "usage";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("save")) {
				if (args.length != 8) {
					throw new WrongUsageException(getUsage(sender) + ".save");
				}
				try {
					String name = stripName(args[1]);
					BlockPos pos1 = parseBlockPos(sender, args, 2, false);
					BlockPos pos2 = parseBlockPos(sender, args, 5, false);
					
					File file = new File(getSchematicPath(), name + ".uschematic");
					
					USchematicSaveRegion region = new USchematicSaveRegion(world, pos1, pos2);
					USchematicWriter writer = new USchematicWriter(region, file);
					writer.finished((success, seconds) -> {
						if (success) {
							notifyCommandListener(player, this, lang + "success.save", pos1, pos2, name, seconds);
						}
					}).start();
					
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new CommandException(lang + "error", "saving", ex.getClass().getName());
				}
			} else if (args[0].equalsIgnoreCase("load")) {
				if (args.length != 4 && args.length != 7) {
					throw new WrongUsageException(getUsage(sender) + ".load");
				}
				try {
					String name = stripName(args[1]);
					USchematicRotation rotation = parseRotation(args[2]);
					boolean center = args[3].equalsIgnoreCase("true") ? true : false;
					BlockPos pos;
					
					if (args.length == 4) {
						pos = player.getPosition();
					} else {
						pos = parseBlockPos(sender, args, 4, false);
					}
					
					File file = new File(getSchematicPath(), name + ".uschematic");
					
					USchematicLoadRegion region = new USchematicLoadRegion(world, pos).rotate(rotation);
					if (center) {
						region.center();
					}
					USchematicReader reader = new USchematicReader(region, file);
					reader.finished((success, seconds) -> {
						if (success) {
							notifyCommandListener(player, this, lang + "success.load", name, pos, seconds);
						}
					}).start();
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new CommandException(lang + "error", "loading", ex.getClass().getName());
				}
			} else {
				throw new WrongUsageException(getUsage(sender));
			}
		} else {
			throw new WrongUsageException(getUsage(sender));
		}
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, new String[] { "save", "load" });
		} else {
			if (args[0].equalsIgnoreCase("save")) {
				if (args.length == 2) {
					return getListOfStringsMatchingLastWord(args, "name");
				} else if (args.length >= 3 && args.length <= 5) {
					return getTabCompletionCoordinate(args, 2, pos);
				} else if (args.length >= 5 && args.length <= 8) {
					return getTabCompletionCoordinate(args, 5, pos);
				}
			} else if (args[0].equalsIgnoreCase("load")) {
				if (args.length == 2) {
					try {
						return getListOfStringsMatchingLastWord(args, FileUtil.getFileNamesInDirectionary(getSchematicPath(), ".uschematic", ""));
					} catch (Exception ex) {
					}
				} else if (args.length == 3) {
					return Lists.newArrayList("0", "90", "180", "270");
				} else if (args.length == 4) {
					return Lists.newArrayList("true", "false");
				} else if (args.length >= 1 && args.length <= 7) {
					return getTabCompletionCoordinate(args, 4, pos);
				}
			}
		}
		return super.getTabCompletions(server, sender, args, pos);
	}
	
	private String stripName(String name) {
		return name.replaceAll("[^a-zA-Z0-9_.-]", "");
	}
	
	private File getSchematicPath() {
		File file = new File(FileUtil.getMainDirectory(), "uschematic");
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}
	
	private USchematicRotation parseRotation(String string) {
		if (string.equalsIgnoreCase("90")) {
			return USchematicRotation.ROTATION_90;
		} else if (string.equalsIgnoreCase("180")) {
			return USchematicRotation.ROTATION_180;
		} else if (string.equalsIgnoreCase("270")) {
			return USchematicRotation.ROTATION_270;
		} else {
			return USchematicRotation.ROTATION_0;
		}
	}
	
}
