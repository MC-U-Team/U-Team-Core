package info.u_team.u_team_core.intern.data.provider;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;

public class UCoreLanguagesProvider extends CommonLanguagesProvider {

	public UCoreLanguagesProvider(GenerationData data) {
		super(data);
	}

	@Override
	public void addTranslations() {
		addUTeamCoreCommand();
	}

	private void addUTeamCoreCommand() {
		// Stack info
		// English
		final String stackInfoSuccess = "commands.uteamcore.stackinfo.success.";
		add(stackInfoSuccess + "item", "Item: %s");
		add(stackInfoSuccess + "block", "Block: %s");
		add(stackInfoSuccess + "nbt", "NBT: %s");

		// Ping
		// English
		final String pingSuccess = "commands.uteamcore.ping.success.";
		add(pingSuccess + "self", "Your ping is %sms");
		add(pingSuccess + "other", "%s's ping is %sms");

		// German
		add("de_de", pingSuccess + "self", "Dein Ping ist %sms");
		add("de_de", pingSuccess + "other", "%s's Ping ist %sms");

		// Dim teleport
		// English
		final String dimTeleportSuccess = "commands.uteamcore.dimteleport.success.";
		add(dimTeleportSuccess + "single", "Teleported %s to %s");
		add(dimTeleportSuccess + "multiple", "Teleported %s entities to %s");
		add(dimTeleportSuccess + "position.single", "Teleported %s to %s at %s, %s, %s");
		add(dimTeleportSuccess + "position.multiple", "Teleported %s entities to %s at %s, %s, %s");

		// Jei
		// English
		add("jei.uteamcore.dyeable.info", "These items can be dyed with dye in the crafting field.");

		// Discord config
		// English
		final String discordConfigScreen = "screen.uteamcore.config.discord.";
		add(discordConfigScreen + "title", "U Team Core Discord Config Options");
		add(discordConfigScreen + "on", "Discord connection is on");
		add(discordConfigScreen + "off", "Discord connection is off");
		add(discordConfigScreen + "done", "Done");
	}
}
