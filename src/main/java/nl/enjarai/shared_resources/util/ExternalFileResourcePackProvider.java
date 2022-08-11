package nl.enjarai.shared_resources.util;

import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import nl.enjarai.shared_resources.mixin.FileResourcePackProviderAccessor;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExternalFileResourcePackProvider extends FileResourcePackProvider {
    protected final Supplier<Path> pathSupplier;

    public ExternalFileResourcePackProvider(Supplier<Path> pathSupplier) {
        super(null, ResourcePackSource.PACK_SOURCE_NONE);
        this.pathSupplier = pathSupplier;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory) {
        var thiz = (FileResourcePackProviderAccessor) this;

        thiz.setPacksFolder(pathSupplier.get().toFile());
        if (thiz.getPacksFolder() == null) return;

        super.register(profileAdder, factory);
    }
}
