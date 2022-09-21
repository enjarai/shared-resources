package nl.enjarai.shared_resources.mc19.impl;

import net.minecraft.text.Text;
import nl.enjarai.shared_resources.versioned.TextBuilder;

@SuppressWarnings("unused")
public class TextBuilderImpl implements TextBuilder {
    @Override
    public Text translatable(String key) {
        return Text.translatable(key);
    }

}