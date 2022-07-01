function initializeCoreMod() {
	ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI")

	Opcodes = Java.type("org.objectweb.asm.Opcodes")
	InsList = Java.type("org.objectweb.asm.tree.InsnList")

	LabelNode = Java.type("org.objectweb.asm.tree.LabelNode")
	LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode")

	return {
		"DataProvider#saveStable": {
			"target": {
				"type": "METHOD",
				"class": "net.minecraft.data.DataProvider",
				"methodName": "m_236072_",
				"methodDesc": "(Lnet/minecraft/data/CachedOutput;Lcom/google/gson/JsonElement;Ljava/nio/file/Path;)V"
			},
			"transformer": function(methodNode) {
				if (ASMAPI.getSystemPropertyFlag("uteamcore.dataprovider-set-indent")) {
					replaceIndent(methodNode);
				}
				return methodNode;
			}
		}
	}
}

function replaceIndent(methodNode) {
	var ldc = ASMAPI.findFirstInstruction(methodNode, Opcodes.LDC)
	ldc.cst = "\u0009";
	ASMAPI.log("INFO", "Replaced json indent in DataProvider#saveStable")
}