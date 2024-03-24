package nl.enjarai.shared_resources.compat.mixin.litematica;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.compat.CompatMixin;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;

@SuppressWarnings("UnresolvedMixinReference")
@Pseudo
@CompatMixin("litematica")
@Mixin(targets = "fi.dy.masa.litematica.data.DataManager")
public abstract class DataManagerMixin {
    @Dynamic
    @Inject(
            remap = false,
            method = "getDefaultBaseSchematicDirectory",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void sharedresources$modifyLitematicsDir(CallbackInfoReturnable<File> ci) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.SCHEMATICS);

        if (newDir != null) {
            ci.setReturnValue(newDir.toFile());
        }
    }
}
