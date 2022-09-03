package nl.enjarai.shared_resources.mc18.util;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.enjarai.shared_resources.common.util.TextBuilder;

public class TextBuilderImpl implements TextBuilder {
    @Override
    public Text translatable(String key) {
        return new TranslatableText(key);
    }
}