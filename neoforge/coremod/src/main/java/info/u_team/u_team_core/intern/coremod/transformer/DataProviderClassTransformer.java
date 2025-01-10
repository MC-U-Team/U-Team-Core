package info.u_team.u_team_core.intern.coremod.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import net.neoforged.coremod.api.ASMAPI;
import net.neoforged.coremod.api.ASMAPI.MethodType;

/**
 * Changes spaces to tabs for data provider output
 */
public class DataProviderClassTransformer implements ITransformer<ClassNode> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderClassTransformer.class);
	
	@Override
	public @NotNull TargetType<ClassNode> getTargetType() {
		return TargetType.CLASS;
	}
	
	@Override
	public @NotNull Set<Target<ClassNode>> targets() {
		return Set.of(Target.targetClass("net.minecraft.data.DataProvider"));
	}
	
	@Override
	public @NotNull TransformerVoteResult castVote(ITransformerVotingContext context) {
		return Boolean.getBoolean("coremod.uteamcore.dataprovider-set-indent") ? TransformerVoteResult.YES : TransformerVoteResult.NO;
	}
	
	@Override
	public @NotNull ClassNode transform(ClassNode classNode, ITransformerVotingContext context) {
		classNode.methods.forEach(methodNode -> {
			final LdcInsnNode ldcNode = (LdcInsnNode) ASMAPI.findFirstInstruction(methodNode, Opcodes.LDC);
			if (ldcNode != null && ldcNode.cst.equals("\u0020")) {
				final MethodInsnNode setIndentMethod = ASMAPI.findFirstMethodCall(methodNode, MethodType.VIRTUAL, "com/google/gson/stream/JsonWriter", "setIndent", "(Ljava/lang/String;)V");
				
				// Remove instructions we don't need
				final List<AbstractInsnNode> instructionsForRemoval = new ArrayList<>();
				AbstractInsnNode next = ldcNode.getNext();
				while (next != setIndentMethod) {
					instructionsForRemoval.add(next);
					next = next.getNext();
				}
				instructionsForRemoval.forEach(methodNode.instructions::remove);
				
				ldcNode.cst = "\u0009";
				LOGGER.info("Replaced json indent in {}#{}", classNode.name, methodNode.name);
			}
		});
		return classNode;
	}
}
