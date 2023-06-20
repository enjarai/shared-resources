package nl.enjarai.shared_resources.mc20.impl;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import nl.enjarai.shared_resources.mc20.gui.DirectoryConfigEntry;
import nl.enjarai.shared_resources.mc20.gui.LongButtonWidget;
import nl.enjarai.shared_resources.versioned.ScreenElementsBuilder;

import java.nio.file.Path;
import java.util.function.Consumer;

public class ScreenElementsBuilderImpl implements ScreenElementsBuilder {
    @Override
    public ButtonWidget buildButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(text, onPress)
                .dimensions(x, y, width, height)
                .build();
    }

    @Override
    public ButtonWidget buildLongButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress) {
        return new LongButtonWidget(x, y, width, height, text, onPress);
    }

    @Override
    public AbstractConfigListEntry<Path> getDirectoryEntry(Text fieldName, Path initialValue, Path defaultValue, Consumer<Path> saveConsumer) {
        return new DirectoryConfigEntry(fieldName, initialValue, defaultValue, saveConsumer);
    }
}
