package com.spring.training;

import com.spring.training.model.Message;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


public class MessageSerializationTest {

    @Test
    public void testSerialization() throws IOException {
        Message message = Message.newBuilder()
                .setFrom("laminba2003@gmail.com")
                .setTo("info@gmail.com")
                .setContent("content").build();
        DatumWriter<Message> MessageDatumWriter = new SpecificDatumWriter<Message>(Message.class);
        DataFileWriter<Message> dataFileWriter = new DataFileWriter<Message>(MessageDatumWriter);
        dataFileWriter.create(message.getSchema(), new File("messages.avro"));
        dataFileWriter.append(message);
        dataFileWriter.close();;
    }
}
