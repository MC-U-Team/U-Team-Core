package info.u_team.u_team_core.schematic;

import java.io.*;
import java.util.function.BiConsumer;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.util.io.NBTStreamUtil;

public class SchematicWriter {
	
	private SchematicSaveRegion region;
	private OutputStream stream;
	private BiConsumer<Boolean, Long> consumer;
	
	public SchematicWriter(SchematicSaveRegion region, File file) throws IOException {
		this(region, new FileOutputStream(file));
	}
	
	public SchematicWriter(SchematicSaveRegion region, OutputStream stream) throws IOException {
		this.region = region;
		this.stream = stream;
	}
	
	public SchematicWriter start() {
		startSaver();
		return this;
	}
	
	public SchematicWriter finished(BiConsumer<Boolean, Long> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	private void startSaver() {
		Thread thread = new Thread(() -> {
			boolean success = true;
			long time = System.currentTimeMillis(); // Time measurement
			
			try {
				NBTStreamUtil.writeNBTToStream(region.saveNBT(), stream);
			} catch (IOException ex) {
				UCoreConstants.LOGGER.error("Error while trying to save schematic region.", ex);
				success = false;
			}
			
			consumer.accept(success, System.currentTimeMillis() - time);
		});
		thread.setName("Schematic Saver");
		thread.start();
	}
	
}
