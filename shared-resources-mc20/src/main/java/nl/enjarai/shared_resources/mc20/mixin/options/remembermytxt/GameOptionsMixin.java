package nl.enjarai.shared_resources.mc20.mixin.options.remembermytxt;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.nbt.NbtCompound;
import nl.enjarai.shared_resources.common.SharedResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    /*
     * Most of this code is copied from RememberMyTxt with permission from DuncanRuns.
     * See https://github.com/DuncanRuns/RememberMyTxt
     *
     * This is the 1.19 version of the mixin.
     */

    @Shadow
    protected abstract void accept(GameOptions.Visitor visitor);

    private NbtCompound sharedresources$loadedData;
    private Map<String, String> sharedresources$unacceptedOptions = new HashMap<>();

    @Inject(method = "update", at = @At("HEAD"))
    private void sharedresources$getKeys(NbtCompound nbtCompound, CallbackInfoReturnable<NbtCompound> cir) {
        sharedresources$loadedData = nbtCompound;
    }

    @Inject(
            method = "load",
            at = @At("TAIL")
    )
    private void sharedresources$endLoad(CallbackInfo info) {
        Set<String> unacceptedKeys = sharedresources$loadedData.getKeys();
        accept(new GameOptions.Visitor() {
            @Override
            public <T> void accept(String key, SimpleOption<T> option) {
                unacceptedKeys.remove(key);
            }

            @Override
            public int visitInt(String key, int current) {
                unacceptedKeys.remove(key);
                return current;
            }

            @Override
            public boolean visitBoolean(String key, boolean current) {
                unacceptedKeys.remove(key);
                return current;
            }

            @Override
            public String visitString(String key, String current) {
                unacceptedKeys.remove(key);
                return current;
            }

            @Override
            public float visitFloat(String key, float current) {
                unacceptedKeys.remove(key);
                return current;
            }

            @Override
            public <T> T visitObject(String key, T current, Function<String, T> decoder, Function<T, String> encoder) {
                unacceptedKeys.remove(key);
                return current;
            }
        });
        unacceptedKeys.remove("version");
        sharedresources$unacceptedOptions = new HashMap<>();
        for (String key : unacceptedKeys.toArray(new String[0])) {
            SharedResources.LOGGER.info(
                    "Unaccepted options.txt Key: \"" + key + "\" with value: " + sharedresources$loadedData.get(key) +
                    ". Storing seperately to ensure it is not lost."
            );
            sharedresources$unacceptedOptions.put(key, sharedresources$loadedData.getString(key));
        }
    }

    @Inject(
            method = "write",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;accept(Lnet/minecraft/client/option/GameOptions$Visitor;)V",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void sharedresources$writeUnaccepted(CallbackInfo info, PrintWriter printWriter) {
        // Unaccepted variables will be placed at the top in case they weren't accepted by the visitor during reading.
        // This probably means that they will be written a second time later in the file, and for duplicate keys, the
        // lowest one in the file is the one which will be loaded.
        for (Map.Entry<String, String> entry : sharedresources$unacceptedOptions.entrySet()) {
            printWriter.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
