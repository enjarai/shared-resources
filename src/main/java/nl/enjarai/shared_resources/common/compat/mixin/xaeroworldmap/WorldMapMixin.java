package nl.enjarai.shared_resources.common.compat.mixin.xaeroworldmap;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.compat.CompatMixin;
import nl.enjarai.shared_resources.common.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.nio.file.Path;

@Pseudo
@CompatMixin("xaeroworldmap")
@Mixin(targets = "xaero.map.WorldMap")
public abstract class WorldMapMixin {
    @Dynamic
    @ModifyExpressionValue(
            remap = false,
            method = "loadClient",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/fabricmc/loader/api/FabricLoader;getGameDir()Ljava/nio/file/Path;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;",
                    ordinal = 0
            )
    )
    private Path sharedresources$modifyConfigLocation(Path original) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.XAEROS_WORLDMAP_CONFIG);

        if (newDir != null) {
            return newDir;
        }

        return original;
    }

    @Dynamic
    @ModifyExpressionValue(
            remap = false,
            method = "loadClient",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/fabricmc/loader/api/FabricLoader;getGameDir()Ljava/nio/file/Path;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;",
                    ordinal = 1
            )
    )
    private Path sharedresources$modifyDataLocation(Path original) {
        Path newDir = GameResourceHelper.getPathFor(GameResources.XAEROS_WORLDMAP_DATA);

        if (newDir != null) {
            return newDir;
        }

        return original;
    }
}
