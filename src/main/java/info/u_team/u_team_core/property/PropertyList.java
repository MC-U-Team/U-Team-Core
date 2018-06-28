package info.u_team.u_team_core.property;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;

import info.u_team.u_team_core.api.*;
import net.minecraft.block.properties.PropertyHelper;

public class PropertyList<T extends IUPropertyList & Comparable<T>> extends PropertyHelper<T> {
	
	private final ImmutableSet<T> values;
	private final Map<String, T> map = Maps.<String, T> newHashMap();
	
	protected PropertyList(String propertyname, Class<T> clazz, Collection<T> values) {
		super(propertyname, clazz);
		this.values = ImmutableSet.copyOf(values);
		
		for (T value : values) {
			String name = value.getName();
			if (map.containsKey(name)) {
				throw new IllegalArgumentException("Multiple values have the same name '" + name + "'");
			}
			map.put(name, value);
		}
	}
	
	@Override
	public Collection<T> getAllowedValues() {
		return values;
	}
	
	@Override
	public Optional<T> parseValue(String value) {
		return Optional.<T> fromNullable(map.get(value));
	}
	
	@Override
	public String getName(T value) {
		return value.getName();
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof PropertyList && super.equals(object)) {
			PropertyList<?> propertyenum = (PropertyList<?>) object;
			return values.equals(propertyenum.values) && map.equals(propertyenum.map);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int i = super.hashCode();
		i = 31 * i + values.hashCode();
		i = 31 * i + map.hashCode();
		return i;
	}
	
	public static <T extends IUPropertyList & Comparable<T>> PropertyList<T> create(String name, Class<T> clazz, Collection<T> values) {
		return new PropertyList<T>(name, clazz, values);
	}
}