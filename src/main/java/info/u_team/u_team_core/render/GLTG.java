/*******************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package info.u_team.u_team_core.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Draw class for a higher level drawing
 * 
 * @author MrTroble
 * @date 17.9.2017
 */

public class GLTG {
	
	public static GLSize setTexture(ResourceLocation location) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(location);
		return new GLSize(GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH), GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT));
	}
	
	public static void drawManual(float[][] vertex, float[][] color, float[][] tex, float[][] normal) {
		DrawFormat form;
		if (color == null) {
			if (normal == null) {
				form = DrawFormat.POS_TEX;
			} else {
				form = DrawFormat.POS_TEX_NORMAL;
			}
		} else if (tex == null) {
			form = DrawFormat.POS_COLOR;
		} else if (normal == null) {
			form = DrawFormat.POS_TEX_COLOR;
		} else {
			form = DrawFormat.POS_TEX_COLOR_NORMAL;
		}
		BufferEntry.start(form);
		int i = -1;
		for (float[] vert : vertex) {
			i++;
			try {
				BufferEntry.vertex(vert[0], vert[1], vert[2]);
				if (color != null && (form == DrawFormat.POS_COLOR || form == DrawFormat.POS_TEX_COLOR || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					BufferEntry.color(color[i][0], color[i][1], color[i][2], color[i][3]);
				}
				if (tex != null && (form == DrawFormat.POS_TEX || form == DrawFormat.POS_TEX_COLOR || form == DrawFormat.POS_TEX_NORMAL || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					BufferEntry.tex(tex[i][0], tex[i][1]);
				}
				if (normal != null && (form == DrawFormat.POS_TEX_NORMAL || form == DrawFormat.POS_TEX_COLOR_NORMAL)) {
					BufferEntry.normal(normal[i][0], normal[i][1], normal[i][2]);
				}
				BufferEntry.end();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("LOL");
			}
		}
		BufferEntry.endDraw();
	}
	
	public static float[] vertex(float... data) {
		return data;
	}
	
	public static float[][] data(float[]... data) {
		return data;
	}
	
}
