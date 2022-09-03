package nl.enjarai.shared_resources.common.config.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import nl.enjarai.shared_resources.common.util.directory.EmptyGameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.RootedGameDirectoryProvider;

import java.io.IOException;
import java.nio.file.Path;

public class GameDirectoryProviderAdapter extends TypeAdapter<GameDirectoryProvider> {

    @Override
    public void write(JsonWriter out, GameDirectoryProvider gameDirectoryProvider) throws IOException {

        var value = "";

        if (gameDirectoryProvider instanceof RootedGameDirectoryProvider rooted) {
            value = rooted.getRoot().toString();
        }

        out.beginObject();
        out.name("root");
        out.value(value);
        out.endObject();
    }

    @Override
    public GameDirectoryProvider read(JsonReader in) throws IOException {
        GameDirectoryProvider result = null;

        in.beginObject();
        while (in.hasNext()) {
            var name = in.nextName();

            if (name.equals("root")) {
                var root = in.nextString();

                result = new RootedGameDirectoryProvider(Path.of(root));
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return result != null ? result : new EmptyGameDirectoryProvider();
    }
}
