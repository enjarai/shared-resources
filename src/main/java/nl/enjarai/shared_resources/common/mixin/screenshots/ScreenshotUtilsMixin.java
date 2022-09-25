package nl.enjarai.shared_resources.common.mixin.screenshots;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;

@Mixin(targets = "net/minecraft/client/util/ScreenshotUtils")
public abstract class ScreenshotUtilsMixin {

    @ModifyVariable(
            method = "saveScreenshotInner(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/gl/Framebuffer;Ljava/util/function/Consumer;)V",
            at = @At(value = "STORE"),
            index = 7
    )
    private static File sharedresources$modScreenshotDir(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.SCREENSHOTS, file.toPath()).toFile();
    }
}
