package nl.enjarai.shared_resources.common.mixin.resourcepacks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.File;

@Mixin(PackScreenMixin.class)
public abstract class PackScreenMixin {
    @ModifyExpressionValue(
            method = "init()V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/pack/PackScreen;file:Ljava/io/File;"
            )
    )
    private File sharedresources$overrideOpenPackFolder(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.RESOURCEPACKS, file.toPath()).toFile();
    }
}
