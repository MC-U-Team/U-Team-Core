function initializeCoreMod() {
	ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI")

	Opcodes = Java.type("org.objectweb.asm.Opcodes")
	InsList = Java.type("org.objectweb.asm.tree.InsnList")

	Label = Java.type("org.objectweb.asm.Label")

	LabelNode = Java.type("org.objectweb.asm.tree.LabelNode")
	VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode")
	TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode")
	JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode")


	return {
		"ServerPlayer#initMenu": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.server.level.ServerPlayer",
				"methodName": "m_143399_",
				"methodDesc": "(Lnet/minecraft/world/inventory/AbstractContainerMenu;)V"
			},
			"transformer": function(methodNode) {
				injectUContainerMenuInitMenu(methodNode);
				return methodNode;
			}
		}
	}
}

function injectUContainerMenuInitMenu(methodNode) {
	var insList = new InsList()

	insList.add(new LabelNode())
	insList.add(new VarInsnNode(Opcodes.ALOAD, 1))
	insList.add(new VarInsnNode(Opcodes.ALOAD, 0))
	insList.add(ASMAPI.buildMethodCall(
		"info/u_team/u_team_core/intern/asm/ASMUContainerMenuHook",
		"hook",
		"(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/server/level/ServerPlayer;)V",
		ASMAPI.MethodType.STATIC
	))

	var returnIns = ASMAPI.findFirstInstruction(methodNode, Opcodes.RETURN)
	methodNode.instructions.insertBefore(returnIns, insList)

	ASMAPI.log("INFO", "Injected ASMUContainerMenuHook call into ServerPlayer#initMenu")
}