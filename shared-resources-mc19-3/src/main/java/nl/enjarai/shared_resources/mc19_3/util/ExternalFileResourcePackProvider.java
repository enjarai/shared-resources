package nl.enjarai.shared_resources.mc19_3.util;

import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import nl.enjarai.shared_resources.versioned.FileResourcepackProviderProxy;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExternalFileResourcePackProvider extends FileResourcePackProvider {
    protected final Supplier<Path> pathSupplier;

    public ExternalFileResourcePackProvider(Supplier<Path> pathSupplier) {
        super(null, ResourceType.CLIENT_RESOURCES, ResourcePackSource.NONE);
        this.pathSupplier = pathSupplier;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        FileResourcepackProviderProxy thiz = (FileResourcepackProviderProxy) this;

        Path path = pathSupplier.get();
        if (path == null) return;
        thiz.sharedresources$setPacksFolder(path);

        super.register(profileAdder);
    }
}
