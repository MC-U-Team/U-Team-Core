function initializeCoreMod() {
	ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI")

	Opcodes = Java.type("org.objectweb.asm.Opcodes")
	InsList = Java.type("org.objectweb.asm.tree.InsnList")

	LabelNode = Java.type("org.objectweb.asm.tree.LabelNode")
	VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode")
	InsnNode = Java.type("org.objectweb.asm.tree.InsnNode")

	return {
		"AbstractContainerScreen#mouseDragged": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.client.gui.screens.inventory.AbstractContainerScreen",
				"methodName": "m_7979_",
				"methodDesc": "(DDIDD)Z"
			},
			"transformer": function(methodNode) {
				injectSuperCall(methodNode);
				return methodNode;
			}
		}
	}
}

function injectSuperCall(methodNode) {
	var insList = new InsList()

	// Build method call to super.mouseDragged with the right parameters
	insList.add(new LabelNode())
	insList.add(new VarInsnNode(Opcodes.ALOAD, 0))
	insList.add(new VarInsnNode(Opcodes.DLOAD, 1))
	insList.add(new VarInsnNode(Opcodes.DLOAD, 3))
	insList.add(new VarInsnNode(Opcodes.ILOAD, 5))
	insList.add(new VarInsnNode(Opcodes.DLOAD, 6))
	insList.add(new VarInsnNode(Opcodes.DLOAD, 8))
	insList.add(ASMAPI.buildMethodCall(
		"net/minecraft/client/gui/screens/Screen",
		ASMAPI.mapMethod("m_7979_"),
		"(DDIDD)Z",
		ASMAPI.MethodType.SPECIAL
	))
	insList.add(new InsnNode(Opcodes.POP))

	// Insert our method call at the top of the method
	methodNode.instructions.insert(insList)

	ASMAPI.log("INFO", "Injected Mousedragged super call into {}#{}", "AbstractContainerScreen", methodNode.name)
}