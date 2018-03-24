package info.u_team.u_team_core.render;

import org.lwjgl.opengl.GL11;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.client.renderer.*;

/**
 * Entry for the MC standard VertexBuffer
 * 
 * @author MrTroble
 * @date 17.9.2017
 *
 */

public class BufferEntry {
	
	private static BufferEntry INSTANCE;
	
	public static BufferEntry getBufferEntry() {
		if (INSTANCE == null)
			INSTANCE = new BufferEntry();
		return INSTANCE;
	}
	
	private Tessellator tesllator;
	private WorldRenderer buffer;
	
	public BufferEntry() {
		this.tesllator = Tessellator.getInstance();
		this.buffer = this.tesllator.getWorldRenderer();
	}
	
	public BufferEntry vertex(float x, float y, float z) {
		this.buffer.addVertex(x, y, z);
		return this;
	}
	
	public BufferEntry tex(float u, float v) {
		this.buffer.setTextureUV(u, v);
		return this;
	}
	
	public BufferEntry color(float r, float b, float g, float a) {
		this.buffer.setColorRGBA_F(r, g, b, a);
		return this;
	}
	
	public BufferEntry normal(float x, float y, float z) {
		this.buffer.putNormal(x, y, z);
		return this;
	}
	
	public BufferEntry start(DrawFormat format) {
		if (format == null) {
			UCoreConstants.LOGGER.error("DrawFormar is null in BufferEntry.start()");
		}
		this.buffer.startDrawing(GL11.GL_POLYGON_BIT);
		return this;
	}
	
	public void end() {
	}
	
	public void endDraw() {
		this.tesllator.draw();
	}
}
