package nl.enjarai.shared_resources.mixin.saves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.nio.file.Path;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @ModifyExpressionValue(
            method = "<init>",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=saves"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;",
                    ordinal = 0
            )
    )
    private Path sharedresources$changePath(Path original) {
        Path newPath = GameResourceHelper.getPathFor(GameResources.SAVES);

        if (newPath != null) {
            return newPath;
        }

        return original;
    }
}
