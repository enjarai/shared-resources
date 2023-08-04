package nl.enjarai.shared_resources.common.compat.replaymod.mixin;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Pseudo
@Mixin(targets = "com.replaymod.core.files.ReplayFoldersService")
public abstract class ReplayFoldersServiceMixin {
    @Dynamic
    @Inject(
            remap = false,
            method = "getReplayFolder",
            at = @At("HEAD"),
            cancellable = true
    )
    private void sharedresources$modifyReplayFolder(CallbackInfoReturnable<Path> ci) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.REPLAY_RECORDINGS);

        if (newDir != null) {
            ci.setReturnValue(newDir);
        }
    }
}
