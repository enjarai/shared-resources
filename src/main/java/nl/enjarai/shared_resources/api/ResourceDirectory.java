package nl.enjarai.shared_resources.api;

import net.minecraft.util.Identifier;

import javax.annotation.Nullable;

public interface ResourceDirectory {
    @Nullable
    default Identifier getId() {
        return ResourceDirectoryRegistry.REGISTRY.getId(this);
    }

    String getDefaultSubdirectory();
}
