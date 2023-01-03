function initializeCoreMod() {
	ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI")

	Opcodes = Java.type("org.objectweb.asm.Opcodes")

	return {
		"DataProvider": {
			"target": {
				"type": "CLASS",
				"name": "net.minecraft.data.DataProvider"
			},
			"transformer": function(classNode) {
				if (ASMAPI.getSystemPropertyFlag("uteamcore.dataprovider-set-indent")) {
					replaceIndent(classNode);
				}
				return classNode;
			}
		}
	}
}

function replaceIndent(classNode) {
	classNode.methods.forEach(function(methodNode) {
		var ldc = ASMAPI.findFirstInstruction(methodNode, Opcodes.LDC)
		if (ldc != null && ldc.cst.equals("\u0020\u0020")) {
			ldc.cst = "\u0009";
			ASMAPI.log("INFO", "Replaced json indent in {}#{}", classNode.name, methodNode.name)
		}
	});
}