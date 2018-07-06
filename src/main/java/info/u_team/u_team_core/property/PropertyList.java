/*-*****************************************************************************
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

package info.u_team.u_team_core.property;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;

import info.u_team.u_team_core.api.IPropertyList;
import net.minecraft.block.properties.PropertyHelper;

/**
 * Property list used for block states (like an enum but needs only an array)
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class PropertyList<T extends IPropertyList & Comparable<T>> extends PropertyHelper<T> {
	
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
			PropertyList<?> property = (PropertyList<?>) object;
			return values.equals(property.values) && map.equals(property.map);
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
	
	public static <T extends IPropertyList & Comparable<T>> PropertyList<T> create(String name, Class<T> clazz, @SuppressWarnings("unchecked") T... values) {
		return create(name, clazz, Lists.newArrayList(values));
	}
	
	public static <T extends IPropertyList & Comparable<T>> PropertyList<T> create(String name, Class<T> clazz, Collection<T> values) {
		return new PropertyList<T>(name, clazz, values);
	}
}
