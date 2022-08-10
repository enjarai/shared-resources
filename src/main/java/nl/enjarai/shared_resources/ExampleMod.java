package nl.enjarai.shared_resources;

import net.fabricmc.api.ModInitializer;
import nl.enjarai.shared_resources.util.ExternalFileResourcePackProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ExampleMod implements ModInitializer {
	public static final String MODID = "modid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final ExternalFileResourcePackProvider GLOBAL_RP_PROVIDER = new ExternalFileResourcePackProvider();

	@Override
	public void onInitialize() {
		GLOBAL_RP_PROVIDER.setPacksFolder(new File("packs"));

	}
}
