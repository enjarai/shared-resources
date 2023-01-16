package nl.enjarai.shared_resources.mc19_2.mixin.resourcepacks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screen.pack.PackScreen;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.File;

@Mixin(PackScreen.class)
public abstract class PackScreenMixin {
    @ModifyExpressionValue(
            method = "method_29670",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/pack/PackScreen;file:Ljava/io/File;"
            )
    )
    private File sharedresources$overrideOpenPackFolder(File file) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.RESOURCEPACKS, file.toPath()).toFile();
    }
}
