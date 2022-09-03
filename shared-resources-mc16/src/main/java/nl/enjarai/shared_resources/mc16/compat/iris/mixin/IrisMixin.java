package nl.enjarai.shared_resources.mc16.compat.iris.mixin;

import nl.enjarai.shared_resources.common.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.compat.iris.IrisMixinHooks;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Files;
import java.nio.file.Path;

@Pseudo
@Mixin(targets = "net.coderbot.iris.Iris")
public abstract class IrisMixin {
    @Inject(
            method = "getShaderpacksDirectory()Ljava/nio/file/Path;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void shared_resources$injectShaderpacksDirectory(CallbackInfoReturnable<Path> ci) {

        if (IrisMixinHooks.fixShaderpackFolders > 0) {
            IrisMixinHooks.fixShaderpackFolders--;

            var source = GameResourceHelper.getPathFor(GameResources.SHADERPACKS);
            if (source != null) {
                ci.setReturnValue(source);
            }
        }
    }

    @Inject(
            method = "loadExternalShaderpack(Ljava/lang/String;)Z",
            at = @At(value = "HEAD")
    )
    private static void shared_resources$fixShaderpackDirectory(String name, CallbackInfoReturnable<Boolean> ci) {

        var source = GameResourceHelper.getPathFor(GameResources.SHADERPACKS);
        if (source == null) return;

        if (Files.exists(source.resolve(name))) {
            IrisMixinHooks.fixShaderpackFolders += 2;
        }
    }
}
