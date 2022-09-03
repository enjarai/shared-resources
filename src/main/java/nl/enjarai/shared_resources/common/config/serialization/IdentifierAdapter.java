package nl.enjarai.shared_resources.common.config.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class IdentifierAdapter extends TypeAdapter<Identifier> {
    @Override
    public void write(JsonWriter out, Identifier identifier) throws IOException {
        out.value(identifier.toString());
    }

    @Override
    public Identifier read(JsonReader in) throws IOException {
        return new Identifier(in.nextString());
    }
}
