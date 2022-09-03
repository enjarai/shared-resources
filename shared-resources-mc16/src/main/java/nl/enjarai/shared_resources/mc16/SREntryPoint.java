package nl.enjarai.shared_resources.mc16;

import net.fabricmc.api.ModInitializer;
import nl.enjarai.shared_resources.common.SharedResources;
import nl.enjarai.shared_resources.mc16.util.TextBuilderImpl;

public class SREntryPoint implements ModInitializer {
    @Override
    public void onInitialize() {
        SharedResources.TEXT_BUILDER = new TextBuilderImpl();
    }
}
