package info.u_team.u_team_core.property;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;

import info.u_team.u_team_core.api.IUMetaType;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyList<T extends IUMetaType & Comparable<T>> extends PropertyHelper<T> {
	
	private final ImmutableSet<T> allowedValues;
	private final Map<String, T> nameToValue = Maps.<String, T> newHashMap();
	
	protected PropertyList(String name, Class<T> valueClass, Collection<T> allowedValues) {
		super(name, valueClass);
		this.allowedValues = ImmutableSet.copyOf(allowedValues);
		
		for (T t : allowedValues) {
			String s = t.getName();
			
			if (this.nameToValue.containsKey(s)) {
				throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
			}
			
			this.nameToValue.put(s, t);
		}
	}
	
	public Collection<T> getAllowedValues() {
		return this.allowedValues;
	}
	
	public Optional<T> parseValue(String value) {
		return Optional.<T> fromNullable(this.nameToValue.get(value));
	}
	
	/**
	 * Get the name for the given value.
	 */
	public String getName(T value) {
		return value.getName();
	}
	
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ instanceof PropertyList && super.equals(p_equals_1_)) {
			PropertyList<?> propertyenum = (PropertyList<?>) p_equals_1_;
			return this.allowedValues.equals(propertyenum.allowedValues) && this.nameToValue.equals(propertyenum.nameToValue);
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		int i = super.hashCode();
		i = 31 * i + this.allowedValues.hashCode();
		i = 31 * i + this.nameToValue.hashCode();
		return i;
	}
	
	public static <T extends IUMetaType & Comparable<T>> PropertyList<T> create(String name, Class<T> clazz, Collection<T> values) {
		return new PropertyList<T>(name, clazz, values);
	}
}