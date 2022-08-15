package nl.enjarai.shared_resources.mixin.resourcepacks;

import net.minecraft.resource.FileResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(FileResourcePackProvider.class)
public interface FileResourcePackProviderAccessor {
    @Accessor("packsFolder")
    File getPacksFolder();

    @Accessor("packsFolder")
    @Mutable
    void setPacksFolder(File packsFolder);
}
