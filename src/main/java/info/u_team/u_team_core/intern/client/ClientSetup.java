package info.u_team.u_team_core.intern.client;

import java.util.*;
import java.util.concurrent.*;

import org.lwjgl.opengl.*;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 * Setup all data for Client
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class ClientSetup {
	
	private Client client;
	
	public ClientSetup() {
		client = new Client();
		firstSend();
		keepAlive();
	}
	
	private void firstSend() {
		client.firstSend(fetchdata());
	}
	
	private void keepAlive() {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			client.keepAlive();
		}, 150, 150, TimeUnit.SECONDS);
	}
	
	private Map<String, String> fetchdata() {
		String country, language, cpu, system, display, graphicscard, graphicsdriver, javaversion, javabit, ram, minecraftversion, forgeversion, uteamcoreversion, deobfuscatedenvironment, mods;
		
		country = System.getProperty("user.country");
		language = System.getProperty("user.language");
		
		cpu = getCpu();
		system = System.getProperty("os.name");
		display = Display.getWidth() + "x" + Display.getHeight() + " " + GL11.glGetString(7936);
		graphicscard = GL11.glGetString(7937);
		graphicsdriver = GL11.glGetString(7938);
		
		javaversion = System.getProperty("java.version");
		javabit = "" + (Minecraft.getMinecraft().isJava64bit() ? 64 : 32);
		ram = "" + Math.round((Runtime.getRuntime().maxMemory() / 1024L / 1024L));
		minecraftversion = ForgeVersion.mcVersion;
		forgeversion = ForgeVersion.getVersion();
		uteamcoreversion = UCoreConstants.VERSION;
		deobfuscatedenvironment = ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")).toString();
		mods = "";
		for (ModContainer container : Loader.instance().getModList()) {
			mods = mods + container.getModId() + "@" + container.getVersion() + ";";
		}
		mods = mods.substring(0, mods.length() - 1); // Should work, because fml mods are always present.
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("country", country);
		map.put("language", language);
		map.put("cpu", cpu);
		map.put("system", system);
		map.put("display", display);
		map.put("graphicscard", graphicscard);
		map.put("graphicsdriver", graphicsdriver);
		map.put("javaversion", javaversion);
		map.put("javabit", javabit);
		map.put("ram", ram);
		map.put("minecraftversion", minecraftversion);
		map.put("forgeversion", forgeversion);
		map.put("uteamcoreversion", uteamcoreversion);
		map.put("deobfuscatedenvironment", deobfuscatedenvironment);
		map.put("mods", mods);
		
		return map;
	}
	
	private String getCpu() {
		CentralProcessor aprocessor = (new SystemInfo()).getHardware().getProcessor();
		return String.format("%dx %s", new Object[] { Integer.valueOf(aprocessor.getLogicalProcessorCount()), aprocessor.toString() }).replaceAll("\\s+", " ");
	}
	
}
