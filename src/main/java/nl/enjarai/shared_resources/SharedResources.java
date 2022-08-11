package nl.enjarai.shared_resources;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.ResourceDirectory;
import nl.enjarai.shared_resources.api.ResourceDirectoryBuilder;
import nl.enjarai.shared_resources.api.ResourceDirectoryHelper;
import nl.enjarai.shared_resources.api.ResourceDirectoryRegistry;
import nl.enjarai.shared_resources.util.ExternalFileResourcePackProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedResources implements ModInitializer {
	public static final String MODID = "shared-resources";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final ResourceDirectory RP_DIRECTORY = ResourceDirectoryRegistry.REGISTRY.register(
			id("resourcepacks"), new ResourceDirectoryBuilder().setDefaultSubdirectory("resourcepacks").build());

	public static final ExternalFileResourcePackProvider GLOBAL_RP_PROVIDER =
			new ExternalFileResourcePackProvider(() -> ResourceDirectoryHelper.getDirectory(RP_DIRECTORY));

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
