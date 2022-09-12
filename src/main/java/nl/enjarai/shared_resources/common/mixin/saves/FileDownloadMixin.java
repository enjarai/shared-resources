package nl.enjarai.shared_resources.common.mixin.saves;

import net.minecraft.client.realms.FileDownload;
import nl.enjarai.shared_resources.common.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;
import java.nio.file.Path;

@Mixin(FileDownload.class)
public abstract class FileDownloadMixin {
    @ModifyVariable(
            method = "untarGzipArchive(Ljava/lang/String;Ljava/io/File;Lnet/minecraft/world/level/storage/LevelStorage;)V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "java/io/File.<init>(Ljava/lang/String;Ljava/lang/String;)V"
            ),
            argsOnly = true
    )
    private File shared_resources_changePath(File original) {
        Path newPath = GameResourceHelper.getPathFor(GameResources.SAVES);

        if (newPath != null) {
            return newPath.toFile();
        }

        return original;
    }
}
