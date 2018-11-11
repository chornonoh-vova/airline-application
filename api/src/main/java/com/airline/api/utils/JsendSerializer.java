package com.airline.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class JsendSerializer extends StdSerializer<JsendResponse> {
  public JsendSerializer() {
    this(null);
  }

  public JsendSerializer(Class<JsendResponse> t) {
    super(t);
  }

  @Override
  public void serialize(JsendResponse value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("status", value.getStatus());
    if (value.getStatus().equals("error")) {
      gen.writeStringField("message", value.getMessage());
    } else {
      gen.writeObjectField("data", value.getData());
    }
    gen.writeEndObject();
  }
}
