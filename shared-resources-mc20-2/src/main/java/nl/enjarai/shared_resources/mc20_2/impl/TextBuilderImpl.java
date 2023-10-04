package nl.enjarai.shared_resources.mc20_2.impl;

import net.minecraft.text.Text;
import nl.enjarai.shared_resources.versioned.TextBuilder;

public class TextBuilderImpl implements TextBuilder {
    @Override
    public Text translatable(String key, Object... objects) {
        return Text.translatable(key, objects);
    }

}