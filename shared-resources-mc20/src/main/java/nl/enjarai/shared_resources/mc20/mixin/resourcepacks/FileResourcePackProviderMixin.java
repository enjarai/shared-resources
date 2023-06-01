package nl.enjarai.shared_resources.mc20.mixin.resourcepacks;

import net.minecraft.resource.FileResourcePackProvider;
import nl.enjarai.shared_resources.versioned.FileResourcepackProviderProxy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.file.Path;

@Mixin(FileResourcePackProvider.class)
public class FileResourcePackProviderMixin implements FileResourcepackProviderProxy {
    @Mutable
    @Shadow @Final private Path packsDir;

    @Override
    public void sharedresources$setPacksFolder(Path folder) {
        this.packsDir = folder;
    }

    @Override
    public Path sharedresources$getPacksFolder() {
        return packsDir;
    }
}
