package nl.enjarai.shared_resources.mixin;

import net.minecraft.resource.FileResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(FileResourcePackProvider.class)
public interface FileResourcePackProviderAccessor {
    @Accessor("packsFolder")
    File getPacksFolder();

    @Accessor("packsFolder")
    void setPacksFolder(File packsFolder);
}
