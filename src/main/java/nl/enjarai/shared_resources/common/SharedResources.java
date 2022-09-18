package nl.enjarai.shared_resources.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.common.util.TextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedResources implements ModInitializer {
	public static final String MODID = "shared-resources";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static TextBuilder TEXT_BUILDER;

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
