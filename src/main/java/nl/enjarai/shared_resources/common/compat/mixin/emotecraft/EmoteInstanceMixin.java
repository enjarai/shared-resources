package nl.enjarai.shared_resources.common.compat.mixin.emotecraft;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.compat.CompatMixin;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;

@Pseudo
@CompatMixin("emotecraft")
@Mixin(targets = "io.github.kosmx.emotes.executor.EmoteInstance")
public class EmoteInstanceMixin {
    @Dynamic
    @Inject(
            method = "getExternalEmoteDir",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shared_resources$modifyEmojiDir(CallbackInfoReturnable<File> ci) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.EMOTES);

        if (newDir != null) {
            ci.setReturnValue(newDir.toFile());
        }
    }
}
