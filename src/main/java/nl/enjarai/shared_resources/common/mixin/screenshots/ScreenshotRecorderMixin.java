package nl.enjarai.shared_resources.common.mixin.screenshots;

import net.minecraft.client.util.ScreenshotRecorder;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;

@Mixin(ScreenshotRecorder.class)
public abstract class ScreenshotRecorderMixin {

    @ModifyVariable(
            method = "saveScreenshotInner",
            at = @At(value = "STORE"),
            index = 5
    )
    private static File sharedresources$modScreenshotDir(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.SCREENSHOTS, file.toPath()).toFile();
    }
}
