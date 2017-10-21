package info.u_team.u_team_core.intern.updatechecker;

import java.util.*;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.*;

/**
 * Update API<br>
 * -> Update checker
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateChecker {
	
	private Map<String, String> tocheck = new HashMap<>();
	private Map<String, UpdateResult> results = new HashMap<>();
	
	public void addMod(String modid, String url) {
		tocheck.put(modid, url);
	}
	
	public void getResult(String modid) {
		results.get(modid);
	}
	
	public void start() { // internal use only
		tocheck.forEach((modid, url) -> {
			try {
				results.put(modid, new UpdateParser(modid, new UpdateRequest(url).getData()).getResult());
			} catch (Exception ex) {
				UCoreConstants.LOGGER.warn("Updater for mod " + modid + " threw an exception.", ex);
			}
		});
	}
	
	public List<ITextComponent> createChat() {
		
		List<ITextComponent> list = new ArrayList<>();
		
		results.forEach((modid, result) -> {
			if (result.getState() == UpdateState.NEEDUPDATE) {
				
				ModContainer container = Loader.instance().getIndexedModList().get(modid);
				
				ITextComponent component = new TextComponentString("\u00a7eNew version (\u00a77" + result.getNewVersion() + "\u00a7e) for\u00a7a " + container.getName() + " \u00a7eis available for Minecraft " + ForgeVersion.mcVersion + "!\n\u00a7bDownload at: \u00a7a" + result.getDownload());
				Style style = component.getStyle();
				style.setColor(TextFormatting.YELLOW);
				style.setHoverEvent(new HoverEvent(net.minecraft.util.text.event.HoverEvent.Action.SHOW_TEXT, new TextComponentString("\u00a7cClick to download.")));
				style.setClickEvent(new ClickEvent(net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL, result.getDownload()));
				component.setStyle(style);
				
				list.add(component);
			}
		});
		
		return list;
	}
}
