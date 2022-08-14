package nl.enjarai.shared_resources.config.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import nl.enjarai.shared_resources.util.directory.EmptyGameDirectoryProvider;
import nl.enjarai.shared_resources.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.util.directory.RootedGameDirectoryProvider;

import java.io.IOException;
import java.nio.file.Path;

public class GameDirectoryProviderAdapter extends TypeAdapter<GameDirectoryProvider> {

    @Override
    public void write(JsonWriter out, GameDirectoryProvider gameDirectoryProvider) throws IOException {

        var value = "";

        if (gameDirectoryProvider instanceof RootedGameDirectoryProvider rooted) {
            value = rooted.getRoot().toString();
        }

        out.value(value);
    }

    @Override
    public GameDirectoryProvider read(JsonReader in) throws IOException {

        var value = in.nextString();

        if (!value.isEmpty()) {
            var path = Path.of(value);

            return new RootedGameDirectoryProvider(path);
        }

        return new EmptyGameDirectoryProvider();
    }
}
