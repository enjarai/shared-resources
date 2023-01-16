package nl.enjarai.shared_resources.mc16.mixin.resourcepacks;

import net.minecraft.resource.FileResourcePackProvider;
import nl.enjarai.shared_resources.versioned.FileResourcepackProviderProxy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.nio.file.Path;

@Mixin(FileResourcePackProvider.class)
public class FileResourcePackProviderMixin implements FileResourcepackProviderProxy {
    @Mutable
    @Shadow @Final
    private File packsFolder;

    @Override
    public void sharedresources$setPacksFolder(Path folder) {
        packsFolder = folder.toFile();
    }

    @Override
    public Path sharedresources$getPacksFolder() {
        return packsFolder.toPath();
    }
}
