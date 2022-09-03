package nl.enjarai.shared_resources.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import nl.enjarai.shared_resources.config.SharedResourcesConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SharedResourcesConfig.INSTANCE::getScreen;
    }
}
