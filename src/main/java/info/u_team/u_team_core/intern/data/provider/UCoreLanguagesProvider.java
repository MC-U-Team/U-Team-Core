package info.u_team.u_team_core.intern.data.provider;

import info.u_team.u_team_core.data.*;

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
		
		// Locate biome
		final String locateBiomeSuccess = "commands.uteamcore.locatebiome.success";
		final String locateBiomeFailed = "commands.uteamcore.locatebiome.failed";
		
		add(locateBiomeSuccess, "The nearest %s is at %s (%s blocks away)");
		add(locateBiomeFailed, "Could not find a biome of type \"%s\" within reasonable distance");
		
		// Locate structure
		final String locateStructureSuccess = "commands.uteamcore.locatestructure.success";
		final String locateStructureFailed = "commands.uteamcore.locatestructure.failed";
		
		add(locateStructureSuccess, "The nearest %s is at %s (%s blocks away)");
		add(locateStructureFailed, "Could not find that structure nearby");
	}
}
