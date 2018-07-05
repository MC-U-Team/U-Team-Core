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
package info.u_team.u_team_core.util;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.util.NonNullList;

/**
 * NonNullList with public constructor
 * 
 * @author HyCraftHD
 * @date 05.07.2018
 */

public class NonNullListCustom<E> extends NonNullList<E> {
	
	public NonNullListCustom(List<E> delegate) {
		this(delegate, null);
	}
	
	public NonNullListCustom(List<E> delegate, @Nullable E defaulttype) {
		super(delegate, defaulttype);
	}
	
}
