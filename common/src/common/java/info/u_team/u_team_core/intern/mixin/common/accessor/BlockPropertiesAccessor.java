package info.u_team.u_team_core.intern.mixin.common.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockPropertiesAccessor {
	
	@Accessor("isRedstoneConductor")
	BlockBehaviour.StatePredicate isRedstoneConductor();
	
	@Accessor("isRedstoneConductor")
	void setRedstoneConductor(BlockBehaviour.StatePredicate isRedstoneConductor);
		
}
