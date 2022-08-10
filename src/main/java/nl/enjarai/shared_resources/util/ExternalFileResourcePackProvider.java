package nl.enjarai.shared_resources.util;

import nl.enjarai.shared_resources.mixin.FileResourcePackProviderAccessor;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;

import javax.annotation.Nullable;
import java.io.File;
import java.util.function.Consumer;

public class ExternalFileResourcePackProvider extends FileResourcePackProvider {
    public ExternalFileResourcePackProvider() {
        super(null, ResourcePackSource.PACK_SOURCE_NONE);
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory) {
        var thiz = (FileResourcePackProviderAccessor) this;

        if (thiz.getPacksFolder() == null) return;

        super.register(profileAdder, factory);
    }

    public void setPacksFolder(@Nullable File packsFolder) {
        var thiz = (FileResourcePackProviderAccessor) this;

        thiz.setPacksFolder(packsFolder);
    }
}
