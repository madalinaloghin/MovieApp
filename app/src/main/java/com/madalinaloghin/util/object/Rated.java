package com.madalinaloghin.util.object;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by madalina.loghin on 8/11/2017.
 */

public class Rated {

    private static final String KEY_VALUE = "value";

    @SerializedName(KEY_VALUE)
    private float value;


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    /**
     * Created by madalina.loghin on 8/18/2017.
     */

    public static class RatedAdapter extends TypeAdapter<Rated> {


        public Rated read(JsonReader reader) throws IOException {
            Rated rated = null;
            JsonToken token = reader.peek();

            if ((JsonToken.BEGIN_OBJECT).equals(token)) {

                reader.beginObject();
                reader.nextName();

                rated = new Rated();
                try {
                    rated.setValue((float) reader.nextDouble());
                } catch (IllegalStateException jse) {
                    rated = null;
                }
                reader.endObject();

            } else {
                reader.skipValue();
            }

            return rated;
        }


        public void write(JsonWriter out, Rated value) throws IOException {
            if ((value == null)) {
                out.value(false);
            } else {
                out.beginObject();
                out.name(KEY_VALUE);
                out.value(value.getValue());
                out.endObject();
            }
        }


    }
}
