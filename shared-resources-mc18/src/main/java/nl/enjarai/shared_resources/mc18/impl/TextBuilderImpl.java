package nl.enjarai.shared_resources.mc18.impl;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.enjarai.shared_resources.versioned.TextBuilder;

@SuppressWarnings("unused")
public class TextBuilderImpl implements TextBuilder {
    @Override
    public Text translatable(String key) {
        return new TranslatableText(key);
    }
}