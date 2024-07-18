package nl.enjarai.shared_resources.compat.mixin.iris;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.compat.CompatMixin;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Pseudo
@CompatMixin("iris")
@Mixin(targets = "net.irisshaders.iris.Iris")
public abstract class IrisMixin {
    @Unique
    private static int fixShaderpackFolders = 0;

    @Dynamic
    @Inject(
            method = "getShaderpacksDirectory()Ljava/nio/file/Path;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void shared_resources$injectShaderpacksDirectory(CallbackInfoReturnable<Path> ci) {

        if (fixShaderpackFolders > 0) {
            fixShaderpackFolders--;

            Path source = GameResourceHelper.getPathFor(GameResources.SHADERPACKS);
            if (source != null) {
                ci.setReturnValue(source);
            }
        }
    }

    @Dynamic
    @Inject(
            method = "loadExternalShaderpack(Ljava/lang/String;)Z",
            at = @At(value = "HEAD")
    )
    private static void shared_resources$fixShaderpackDirectory(String name, CallbackInfoReturnable<Boolean> ci) {

        Path source = GameResourceHelper.getPathFor(GameResources.SHADERPACKS);
        if (source == null) return;

        if (source.resolve(name).toFile().exists()) {
            fixShaderpackFolders += 2;
        }
    }
}
