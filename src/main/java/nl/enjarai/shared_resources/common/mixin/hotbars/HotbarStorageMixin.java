package nl.enjarai.shared_resources.common.mixin.hotbars;

import net.minecraft.client.option.HotbarStorage;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.File;

@Mixin(HotbarStorage.class)
public abstract class HotbarStorageMixin {
    @ModifyArg(
            method = "load",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtIo;read(Ljava/io/File;)Lnet/minecraft/nbt/NbtCompound;"
            )
    )
    private File sharedresources$updateHotbarLoadPath(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.HOTBARS, file.toPath()).toFile();
    }

    @ModifyArg(
            method = "save",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtIo;write(Lnet/minecraft/nbt/NbtCompound;Ljava/io/File;)V"
            )
    )
    private File sharedresources$updateHotbarSavePath(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.HOTBARS, file.toPath()).toFile();
    }
}
