package nl.enjarai.shared_resources.common.mixin.servers;

import net.minecraft.client.option.ServerList;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.io.File;
import java.nio.file.Path;

@Mixin(ServerList.class)
public abstract class ServerListMixin {

    private File getOverwrittenPath(File original) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.SERVERS, original.toPath()).toFile();
    }

    @ModifyArg(
            method = "loadFile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtIo;read(Ljava/io/File;)Lnet/minecraft/nbt/NbtCompound;"
            )
    )
    private File sharedresources$modifyFileLoad(File file) {
        return getOverwrittenPath(file);
    }

    @ModifyArg(
            method = "saveFile",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/io/File;createTempFile(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/io/File;"
            ),
            index = 2
    )
    private File sharedresources$modifyFileSave1(File file) {
        Path newPath = GameResourceHelper.getPathFor(GameResources.SERVERS);
        if (newPath != null) {
            return newPath.getParent().toFile();
        }
        return file;
    }

    @ModifyArgs(
            method = "saveFile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Util;backupAndReplace(Ljava/io/File;Ljava/io/File;Ljava/io/File;)V"
            )
    )
    private void sharedresources$modifyFileSave2(Args args) {
        args.set(0, getOverwrittenPath(args.get(0)));

        Path newPath = GameResourceHelper.getPathFor(GameResources.SERVERS);
        if (newPath != null) {
            args.set(2, new File(newPath.toFile() + "_old"));
        }
    }
}
