package nl.enjarai.shared_resources;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.ResourceDirectoryHelper;
import nl.enjarai.shared_resources.api.ResourceDirectoryRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;
import nl.enjarai.shared_resources.util.ExternalFileResourcePackProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nl.enjarai.shared_resources.registry.ResourceDirectories.RESOURCEPACKS;

public class SharedResources implements ModInitializer {
	public static final String MODID = "shared-resources";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final ExternalFileResourcePackProvider GLOBAL_RP_PROVIDER =
			new ExternalFileResourcePackProvider(() -> ResourceDirectoryHelper.getPathFor(RESOURCEPACKS));

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getEntrypoints("shared-resources", SharedResourcesEntrypoint.class).forEach(
				entrypoint -> entrypoint.onInitialize(ResourceDirectoryRegistry.REGISTRY));
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
