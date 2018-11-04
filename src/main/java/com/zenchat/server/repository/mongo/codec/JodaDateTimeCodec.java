package com.zenchat.server.repository.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.joda.time.DateTime;

public class JodaDateTimeCodec implements Codec<DateTime> {

    @Override
    public DateTime decode(BsonReader reader, DecoderContext decoderContext) {
        long instance = reader.readDateTime();

        return new DateTime(instance);
    }

    @Override
    public void encode(BsonWriter writer, DateTime dateTime, EncoderContext encoderContext) {
        writer.writeDateTime(dateTime.getMillis());
    }

    @Override
    public Class<DateTime> getEncoderClass() {
        return DateTime.class;
    }
}
