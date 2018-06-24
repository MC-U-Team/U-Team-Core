package info.u_team.u_team_core.render;

import javax.annotation.Nonnull;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.*;

/**
 * Entry for the MC standard VertexBuffer
 * 
 * @author MrTroble
 * @date 17.9.2017
 *
 */

public class BufferEntry {
	
	public static void vertex(float x, float y, float z) {
		Tessellator.getInstance().getBuffer().pos(x, y, z);
	}
	
	public static void tex(float u, float v) {
		Tessellator.getInstance().getBuffer().tex(u, v);
	}
	
	public static void color(float r, float b, float g, float a) {
		Tessellator.getInstance().getBuffer().color(r, g, b, a);
	}
	
	public static void normal(float x, float y, float z) {
		Tessellator.getInstance().getBuffer().normal(x, y, z);
	}
	
	public static void start(@Nonnull DrawFormat format) {
		if (format == null) {
			UCoreConstants.LOGGER.error("DrawFormar is null in BufferEntry.start()");
		}
		VertexFormat v = null;
		switch (format) {
		case POS_COLOR:
			v = DefaultVertexFormats.POSITION_COLOR;
			break;
		case POS_TEX:
			v = DefaultVertexFormats.POSITION_TEX;
			break;
		case POS_TEX_COLOR:
			v = DefaultVertexFormats.POSITION_TEX_COLOR;
			break;
		case POS_TEX_COLOR_NORMAL:
			v = DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL;
			break;
		case POS_TEX_NORMAL:
			v = DefaultVertexFormats.POSITION_TEX_NORMAL;
			break;
		}
		if (v == null) {
			UCoreConstants.LOGGER.error("Somthing went wrong with the draw start (Wrong vertex Format)");
		}
		Tessellator.getInstance().getBuffer().begin(7, v);
	}
	
	public static void end() {
		Tessellator.getInstance().getBuffer().endVertex();
	}
	
	public static void endDraw() {
		Tessellator.getInstance().draw();
	}
}
