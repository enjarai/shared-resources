package nl.enjarai.shared_resources.gui;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import javax.swing.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class DirectoryConfigEntry extends AbstractConfigListEntry<Path> {
    private static final JFileChooser fileChooser = new JFileChooser();

    static {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    private final AtomicReference<Path> path;
    private final Path defaultValue;
    private final Consumer<Path> saveConsumer;

    private final ButtonWidget pathButton;
    private final List<ClickableWidget> widgets;

    public DirectoryConfigEntry(Text fieldName, Path initialValue, Path defaultValue, Consumer<Path> saveConsumer) {
        super(fieldName, false);
        this.path = new AtomicReference<>(initialValue);
        this.defaultValue = defaultValue;
        this.saveConsumer = saveConsumer;

        pathButton = new ButtonWidget(0, 0, 150, 20, Text.translatable("config.shared_resources.directoryEntry"), button -> {
            int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path.set(fileChooser.getSelectedFile().toPath());
            }
        });
        widgets = Lists.newArrayList(pathButton);
    }

    @Override
    public boolean isRequiresRestart() { // TODO check if entries need restart
        return super.isRequiresRestart();
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
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);

        pathButton.x = x + entryWidth - pathButton.getWidth();
        pathButton.y = y;
        pathButton.setMessage(Text.of(path.get().toString()));

        for (ClickableWidget widget : widgets) {
            widget.render(matrices, mouseX, mouseY, delta);
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
