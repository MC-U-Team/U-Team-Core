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

package info.u_team.u_team_core.util;

import java.util.*;

/**
 * Creates a non null list custom with some basic parameters
 * 
 * @author HyCraftHD
 * @date 05.07.2018
 */
public class NonNullListUtil {
	
	public static <E> NonNullListCustom<E> create() {
		return new NonNullListCustom<>(new ArrayList<>());
	}
	
	@SafeVarargs
	public static <E> NonNullListCustom<E> from(E defaultelement, E... elements) {
		return new NonNullListCustom<>(Arrays.asList(elements), defaultelement);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> NonNullListCustom<E> withSize(int size, E fill) {
		Object[] objects = new Object[size];
		Arrays.fill(objects, fill);
		return new NonNullListCustom<E>(Arrays.asList((E[]) objects), fill);
	}
	
}
