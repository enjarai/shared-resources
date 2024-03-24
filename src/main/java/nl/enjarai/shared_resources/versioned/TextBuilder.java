package nl.enjarai.shared_resources.versioned;

import net.minecraft.text.Text;

public interface TextBuilder {
    static Text translatable(String key, Object... objects) {
        return Text.translatable(key, objects);
    }
}
