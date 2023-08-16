package nl.enjarai.shared_resources.common.compat.mixin.skinshuffle;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.compat.CompatMixin;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Pseudo
@CompatMixin("skinshuffle")
@Mixin(targets = "com.mineblock11.skinshuffle.SkinShuffle")
public abstract class SkinShuffleMixin {
    @Shadow(remap = false, aliases = "DATA_DIR")
    private @Mutable static Path DATA_DIR;

    @Dynamic
    @Inject(
            remap = false,
            method = "<clinit>",
            at = @At("RETURN")
    )
    private static void sharedresources$modifySkinShuffleDataDir(CallbackInfo ci) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.SKINSHUFFLE_DATA);

        if (newDir != null) {
            DATA_DIR = newDir;
        }
    }
}
