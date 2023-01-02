package info.u_team.u_team_core.gui.elements;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract sealed class AbstractSliderLogic extends UButton permits USlider {
	
	protected static final OnSliderChange EMPTY_SLIDER = slider -> {
	};
	
	protected final Component prefix;
	protected final Component suffix;
	
	protected final double minValue;
	protected final double maxValue;
	protected double value;
	
	protected final boolean decimalPrecision;
	protected int precision = 1;
	protected final boolean drawDescription;
	
	protected OnSliderChange slider;
	
	public boolean dragging = false;
	
	protected AbstractSliderLogic(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, boolean decimalPrecision, boolean drawDescription, OnSliderChange slider) {
		super(x, y, width, height, prefix, EMTPY_PRESSABLE);
		this.prefix = prefix;
		this.suffix = suffix;
		this.minValue = minValue;
		this.maxValue = maxValue;
		value = (currentValue - minValue) / (maxValue - minValue);
		this.decimalPrecision = decimalPrecision;
		this.drawDescription = drawDescription;
		
		if (drawDescription) {
			final String displayValue;
			
			if (decimalPrecision) {
				displayValue = Double.toString(value * (maxValue - minValue) + minValue);
				precision = Math.min(displayValue.substring(displayValue.indexOf(".") + 1).length(), 4);
			} else {
				displayValue = Integer.toString((int) Math.round(value * (maxValue - minValue) + minValue));
				precision = 0;
			}
			
			setMessage(Component.empty().append(prefix).append(displayValue).append(suffix));
		} else {
			setMessage(Component.empty());
		}
		
		this.slider = slider;
	}
	
	public void setSlider(OnSliderChange slider) {
		this.slider = slider;
	}
	
	public void setSlider(Runnable runnable) {
		slider = slider -> runnable.run();
	}
	
	public void updateSlider() {
		updateSliderText();
		
		slider.onChange(this);
	}
	
	public void updateSliderText() {
		value = Mth.clamp(value, 0, 1);
		
		String displayValue;
		
		if (decimalPrecision) {
			displayValue = Double.toString(value * (maxValue - minValue) + minValue);
			
			if (displayValue.substring(displayValue.indexOf(".") + 1).length() > precision) {
				displayValue = displayValue.substring(0, displayValue.indexOf(".") + precision + 1);
				
				if (displayValue.endsWith(".")) {
					displayValue = displayValue.substring(0, displayValue.indexOf(".") + precision);
				}
			} else {
				while (displayValue.substring(displayValue.indexOf(".") + 1).length() < precision) {
					displayValue = displayValue + "0";
				}
			}
		} else {
			displayValue = Integer.toString((int) Math.round(value * (maxValue - minValue) + minValue));
		}
		
		if (drawDescription) {
			setMessage(Component.empty().append(prefix).append(displayValue).append(suffix));
		}
	}
	
	public int getValueInt() {
		return (int) Math.round(value * (maxValue - minValue) + minValue);
	}
	
	public double getValue() {
		return value * (maxValue - minValue) + minValue;
	}
	
	public void setValue(double value) {
		this.value = (value - minValue) / (maxValue - minValue);
		updateSliderText();
	}
	
	public static interface OnSliderChange {
		
		void onChange(AbstractSliderLogic slider);
	}
}
