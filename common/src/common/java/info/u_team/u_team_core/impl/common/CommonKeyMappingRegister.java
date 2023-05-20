package info.u_team.u_team_core.impl.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.LazyEntry;
import info.u_team.u_team_core.api.registry.client.KeyMappingRegister;
import net.minecraft.client.KeyMapping;

public abstract class CommonKeyMappingRegister implements KeyMappingRegister {
	
	protected final Map<ForgeKeyMappingSimpleEntry, Supplier<KeyMapping>> entries;
	
	protected CommonKeyMappingRegister() {
		entries = new LinkedHashMap<>();
	}
	
	@Override
	public Supplier<KeyMapping> register(Supplier<KeyMapping> supplier) {
		final ForgeKeyMappingSimpleEntry entry = new ForgeKeyMappingSimpleEntry();
		entries.put(entry, supplier);
		return entry;
	}
	
	protected void updateReference(ForgeKeyMappingSimpleEntry entry, KeyMapping key) {
		entry.updateReference(key);
	}
	
	public static class ForgeKeyMappingSimpleEntry implements LazyEntry<KeyMapping> {
		
		private KeyMapping value;
		
		ForgeKeyMappingSimpleEntry() {
		}
		
		void updateReference(KeyMapping key) {
			value = key;
		}
		
		@Override
		public KeyMapping get() {
			Objects.requireNonNull(value, () -> "Key mapping not present");
			return value;
		}
		
		@Override
		public boolean isPresent() {
			return value != null;
		}
	}
}
