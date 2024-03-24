package nl.enjarai.shared_resources.gui;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import nl.enjarai.shared_resources.versioned.TextBuilder;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DirectoryConfigEntry extends AbstractConfigListEntry<Path> {

    private final AtomicReference<Path> path;
    private final Path initialValue;
    private final Path defaultValue;
    private final Consumer<Path> saveConsumer;

    private final ButtonWidget pathButton;
    private final ButtonWidget resetButton;
    private final List<ClickableWidget> widgets;

    public DirectoryConfigEntry(Text fieldName, Path initialValue, Path defaultValue, Consumer<Path> saveConsumer) {
        super(fieldName, false);
        this.path = new AtomicReference<>(initialValue);
        this.initialValue = initialValue;
        this.defaultValue = defaultValue;
        this.saveConsumer = saveConsumer;

        pathButton = new LongButtonWidget(0, 0, 150, 20, TextBuilder.translatable("config.shared_resources.directoryEntry"), button -> {
            Path absolutePath = path.get().isAbsolute() ? path.get() : FabricLoader.getInstance().getGameDir().resolve(path.get());
            String val = TinyFileDialogs.tinyfd_selectFolderDialog("Select directory", absolutePath.toString());

            if (val != null) {
                path.set(Paths.get(val));
            }
        });
        Text resetButtonKey = TextBuilder.translatable("text.cloth-config.reset_value");
        resetButton = ButtonWidget.builder(resetButtonKey, (widget) -> path.set(defaultValue))
                .dimensions(0, 0, MinecraftClient.getInstance().textRenderer.getWidth(resetButtonKey) + 6, 20)
                .build();
        widgets = Lists.newArrayList(pathButton, resetButton);
    }

    @Override
    public boolean isEdited() {
        return super.isEdited() || !path.get().equals(initialValue);
    }

    @Override
    public boolean isRequiresRestart() { // TODO check if entries need restart
        return true;
    }

    @Override
    public Path getValue() {
        return path.get();
    }

    @Override
    public Optional<Path> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public void save() {
        if (saveConsumer != null) {
            saveConsumer.accept(path.get());
        }
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);

        pathButton.x = x;
        pathButton.y = y;
        pathButton.active = isEditable();
        pathButton.setMessage(Text.of(path.get().toString()));
        pathButton.setWidth(entryWidth - resetButton.getWidth() - 2);

        resetButton.x = x + entryWidth - resetButton.getWidth();
        resetButton.y = y;
        resetButton.active = isEditable() && getDefaultValue().isPresent() && !defaultValue.equals(path.get());

        for (ClickableWidget widget : widgets) {
            widget.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public List<? extends Selectable> narratables() {
        return widgets;
    }

    @Override
    public List<? extends Element> children() {
        return widgets;
    }
}
