package nl.enjarai.shared_resources.mixin.options;

import net.minecraft.client.option.GameOptions;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Mutable
    @Shadow @Final private File optionsFile;

    @Inject(
            method = "load()V",
            at = @At(value = "HEAD")
    )
    private void sharedresources$overwriteOptionsPath(CallbackInfo ci) {
        Path newPath = GameResourceHelper.getPathFor(GameResources.OPTIONS);

        if (newPath != null) {
            optionsFile = newPath.toFile();
        }
    }
}
