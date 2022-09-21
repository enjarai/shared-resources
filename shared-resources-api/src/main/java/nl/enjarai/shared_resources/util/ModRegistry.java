package nl.enjarai.shared_resources.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("UnusedReturnValue")
public class ModRegistry<T> {
    private final HashMap<Identifier, T> entries = new HashMap<>();
    private boolean locked = false;

    public T register(Identifier id, T entry) {
        if (locked) throw new IllegalStateException("Registry is finalized");
        entries.put(id, entry);
        return entry;
    }

    public T get(Identifier id) {
        return entries.get(id);
    }

    @Nullable
    public Identifier getId(T entry) {
        for (Identifier id : entries.keySet()) {
            if (entries.get(id) == entry) {
                return id;
            }
        }
        return null;
    }

    public Iterator<T> iterator() {
        return entries.values().iterator();
    }

    public void finalise() {
        locked = true;
    }
}
