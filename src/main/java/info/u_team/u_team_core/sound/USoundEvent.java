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

package info.u_team.u_team_core.sound;

import info.u_team.u_team_core.api.registry.IUSoundEvent;
import net.minecraft.util.*;

/**
 * Sound API<br>
 * -> Basic Soundevent
 * 
 * @date 05.10.2018
 * @author HyCraftHD
 */
public class USoundEvent extends SoundEvent implements IUSoundEvent {
	
	protected String name;
	
	public USoundEvent(ResourceLocation resource) {
		super(resource);
		name = resource.getNamespace();
	}
	
	public USoundEvent(String name, ResourceLocation resource) {
		super(resource);
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
