package nl.enjarai.shared_resources.common.mixin.resourcepacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.util.ExternalFileResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

import static nl.enjarai.shared_resources.common.registry.GameResources.RESOURCEPACKS;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
	@Mutable
	@Shadow @Final private Set<ResourcePackProvider> providers;

	@Inject(
			method = "<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V",
			at = @At(value = "RETURN")
	)
	private void sharedresources$initResourcePackProvider(CallbackInfo ci) {
		// Only add our own provider if this is the manager of client
		// resource packs, we wouldn't want to mess with datapacks
		if (providers.stream().anyMatch(provider -> provider == MinecraftClient.getInstance().getResourcePackProvider())) {

			providers = new HashSet<>(providers);
			providers.add(new ExternalFileResourcePackProvider(() -> GameResourceHelper.getPathFor(RESOURCEPACKS)));
		}
	}
}
