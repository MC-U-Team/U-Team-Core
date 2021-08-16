package info.u_team.u_team_core.util;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;

public class RenderUtil {
	
	public static void enableBlend() {
		RenderSystem.enableBlend();
	}
	
	public static void disableBlend() {
		RenderSystem.disableBlend();
	}
	
	public static void defaultBlendFunc() {
		RenderSystem.defaultBlendFunc();
	}
	
	public static void blendFunc(SourceFactor srcFactor, DestFactor dstFactor) {
		RenderSystem.blendFunc(srcFactor, dstFactor);
	}
	
	public static void blendFuncSeparate(SourceFactor srcFactor, DestFactor dstFactor, SourceFactor srcFactorAlpha, DestFactor dstFactorAlpha) {
		RenderSystem.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
	}
	
	public static void enableScissor(int x, int y, int width, int height) {
		RenderSystem.enableScissor(x, y, width, height);
	}
	
	public static void disableScissor() {
		RenderSystem.disableScissor();
	}
	
	public static void enableTexture() {
		RenderSystem.enableTexture();
	}
	
	public static void disableTexture() {
		RenderSystem.disableTexture();
	}
	
	public static class Matrix4fExtended extends Matrix4f {
		
		public Matrix4fExtended(Matrix4f matrix4f) {
			super(matrix4f);
		}
		
		public float getM00() {
			return m00;
		}
		
		public void setM00(float m00) {
			this.m00 = m00;
		}
		
		public float getM01() {
			return m01;
		}
		
		public void setM01(float m01) {
			this.m01 = m01;
		}
		
		public float getM02() {
			return m02;
		}
		
		public void setM02(float m02) {
			this.m02 = m02;
		}
		
		public float getM03() {
			return m03;
		}
		
		public void setM03(float m03) {
			this.m03 = m03;
		}
		
		public float getM10() {
			return m10;
		}
		
		public void setM10(float m10) {
			this.m10 = m10;
		}
		
		public float getM11() {
			return m11;
		}
		
		public void setM11(float m11) {
			this.m11 = m11;
		}
		
		public float getM12() {
			return m12;
		}
		
		public void setM12(float m12) {
			this.m12 = m12;
		}
		
		public float getM13() {
			return m13;
		}
		
		public void setM13(float m13) {
			this.m13 = m13;
		}
		
		public float getM20() {
			return m20;
		}
		
		public void setM20(float m20) {
			this.m20 = m20;
		}
		
		public float getM21() {
			return m21;
		}
		
		public void setM21(float m21) {
			this.m21 = m21;
		}
		
		public float getM22() {
			return m22;
		}
		
		public void setM22(float m22) {
			this.m22 = m22;
		}
		
		public float getM23() {
			return m23;
		}
		
		public void setM23(float m23) {
			this.m23 = m23;
		}
		
		public float getM30() {
			return m30;
		}
		
		public void setM30(float m30) {
			this.m30 = m30;
		}
		
		public float getM31() {
			return m31;
		}
		
		public void setM31(float m31) {
			this.m31 = m31;
		}
		
		public float getM32() {
			return m32;
		}
		
		public void setM32(float m32) {
			this.m32 = m32;
		}
		
		public float getM33() {
			return m33;
		}
		
		public void setM33(float m33) {
			this.m33 = m33;
		}
	}
}
