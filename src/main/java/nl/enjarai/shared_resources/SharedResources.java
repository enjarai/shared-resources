package nl.enjarai.shared_resources;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import nl.enjarai.cicada.api.util.ProperLogger;
import org.slf4j.Logger;

public class SharedResources implements ModInitializer {
	public static final String MODID = "shared-resources";
	public static final Logger LOGGER = ProperLogger.getLogger(MODID);

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		//? >=1.21 {
		return Identifier.of(MODID, path);
		//?} else {
		/*return new Identifier(MODID, path);
		*///?}
	}

	public static Identifier vanillaId(String path) {
		//? >=1.21 {
		return Identifier.ofVanilla(path);
		//?} else {
		/*return new Identifier(path);
		 *///?}
	}
}
