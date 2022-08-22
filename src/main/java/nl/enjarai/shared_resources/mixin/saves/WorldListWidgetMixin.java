package nl.enjarai.shared_resources.mixin.saves;

import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelSummary;
import nl.enjarai.shared_resources.api.DirectoryResourceHelper;
import nl.enjarai.shared_resources.registry.DirectoryResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Mixin(WorldListWidget.class)
public abstract class WorldListWidgetMixin {

    private LevelStorage customLevelStorage;
    private LevelStorage.LevelList customLevels;

    @Redirect(
            method = "loadLevels()Ljava/util/concurrent/CompletableFuture;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/LevelStorage$LevelList;isEmpty()Z"
            )
    )
    private boolean modifyWorldsEmptyCheck(LevelStorage.LevelList instance) {
        var defaultValue = instance.isEmpty();

        // Check if a custom world directory is set.
        var customDir = DirectoryResourceHelper.getPathFor(DirectoryResources.SAVES);
        if (customDir == null) {
            return defaultValue;
        }

        // Load the custom worlds and store them for later use.
        customLevelStorage = LevelStorage.create(customDir);
        customLevels = customLevelStorage.getLevelList();

        return defaultValue && customLevels.isEmpty();
    }

    @Redirect(
            method = "loadLevels()Ljava/util/concurrent/CompletableFuture;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/LevelStorage;loadSummaries(Lnet/minecraft/world/level/storage/LevelStorage$LevelList;)Ljava/util/concurrent/CompletableFuture;"
            )
    )
    private CompletableFuture<List<LevelSummary>> appendWorlds(LevelStorage instance, LevelStorage.LevelList levels) {
        var defaultFuture = instance.loadSummaries(levels);

        // Check if temporary values are set, meaning there's a custom directory set.
        if (customLevels == null || customLevelStorage == null) {
            return defaultFuture;
        }

        // Get the custom worlds future.
        var customFuture = customLevelStorage.loadSummaries(customLevels);

        // Clear temporary values.
        customLevelStorage = null;
        customLevels = null;

        // Combine and return the two futures.
        return CompletableFuture.allOf(defaultFuture, customFuture).thenApply(v -> {
            var defaultSummaries = defaultFuture.join();
            var customSummaries = customFuture.join();

            // Merge and return the two immutable lists using a stream.
            return Stream.of(defaultSummaries, customSummaries)
                    .flatMap(List::stream)
                    .toList();
        });
    }
}
