package nl.enjarai.shared_resources.mixin.resourcepacks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screen.pack.PackScreen;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;

@Mixin(PackScreen.class)
public abstract class PackScreenMixin {
    @ModifyExpressionValue(
            method = "method_29670",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/pack/PackScreen;file:Ljava/nio/file/Path;"
            )
    )
    private Path sharedresources$overrideOpenPackFolder(Path path) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.RESOURCEPACKS, path);
    }
}
