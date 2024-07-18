package nl.enjarai.shared_resources.compat.mixin.figura;

import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.compat.CompatMixin;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.nio.file.Path;

@Pseudo
@CompatMixin("figura")
@Mixin(targets = "org.figuramc.figura.FiguraMod")
public abstract class FiguraModMixin {
    @Dynamic
    @ModifyArg(
            method = "getFiguraDirectory",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/utils/IOUtils;createDirIfNeeded(Ljava/nio/file/Path;)Ljava/nio/file/Path;"
            ),
            index = 0
    )
    private static Path modifyFiguraDirectory(Path original) {
        return GameResourceHelper.getPathOrDefaultFor(GameResources.FIGURA, original);
    }
}
