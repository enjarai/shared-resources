package nl.enjarai.shared_resources.api;

import net.minecraft.util.Identifier;

public class DefaultGameResources {
    public static ResourceDirectory RESOURCEPACKS;
    public static ResourceDirectory SAVES;
    public static ResourceDirectory CONFIG;
    public static ResourceFile OPTIONS;

    static void load(GameResourceRegistry registry) {
        RESOURCEPACKS   = (ResourceDirectory)   registry.get(new Identifier("shared-resources:resourcepacks"));
        SAVES           = (ResourceDirectory)   registry.get(new Identifier("shared-resources:saves"));
        CONFIG          = (ResourceDirectory)   registry.get(new Identifier("shared-resources:config"));
        OPTIONS         = (ResourceFile)        registry.get(new Identifier("shared-resources:options"));
    }
}
