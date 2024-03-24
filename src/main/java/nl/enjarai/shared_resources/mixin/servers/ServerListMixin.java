package nl.enjarai.shared_resources.mixin.servers;

import net.minecraft.client.option.ServerList;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.io.File;
import java.nio.file.Path;

@Mixin(ServerList.class)
public abstract class ServerListMixin {
    /*? if <1.20.4 {*//*
    @Unique
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
    *//*?} else {*/
    @Unique
    private Path getOverwrittenPath(Path original) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.SERVERS, original);
    }

    @ModifyArg(
            method = "loadFile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtIo;read(Ljava/nio/file/Path;)Lnet/minecraft/nbt/NbtCompound;"
            )
    )
    private Path sharedresources$modifyFileLoad(Path file) {
        return getOverwrittenPath(file);
    }

    @ModifyArg(
            method = "saveFile",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Files;createTempFile(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;"
            ),
            index = 0
    )
    private Path sharedresources$modifyFileSave1(Path file) {
        Path newPath = GameResourceHelper.getPathFor(GameResources.SERVERS);
        if (newPath != null) {
            return newPath.getParent();
        }
        return file;
    }

    @ModifyArgs(
            method = "saveFile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Util;backupAndReplace(Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;)V"
            )
    )
    private void sharedresources$modifyFileSave2(Args args) {
        args.set(0, getOverwrittenPath(args.get(0)));

        Path newPath = GameResourceHelper.getPathFor(GameResources.SERVERS);
        if (newPath != null) {
            args.set(2, newPath.resolveSibling(newPath.getFileName() + "_old"));
        }
    }
    /*?} */
}
