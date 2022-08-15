package nl.enjarai.shared_resources;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedResources implements ModInitializer {
	public static final String MODID = "shared-resources";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
