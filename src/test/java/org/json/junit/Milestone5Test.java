package org.json.junit;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;



public class Milestone5Test {


    @Test
    public void test1() throws IOException{
        FileReader fileReader = new FileReader("src/test/resources/Issue537.xml");
        JSONObject expected = new JSONObject();

        Future<JSONObject> future = XML.toJsonObject_future(fileReader);

        while(!future.isDone()) {
            fileReader = new FileReader("src/test/resources/Issue537.xml");
            expected = XML.toJSONObject(fileReader);
            fileReader.close();
        }

        JSONObject test;
        try{
            test = future.get(2, TimeUnit.SECONDS);
            Files.writeString(Paths.get("milestone5_output.txt"), test.toString());
        } catch (Throwable e) {
            test = new JSONObject();
        }
        assertEquals(expected.toString(), test.toString());

    }


    @Test
    public void test2() throws IOException{
        FileReader fileReader = null;
        JSONObject expected = new JSONObject();

        Future<JSONObject> future = XML.toJsonObject_future(fileReader);

        while(!future.isDone()) {
            fileReader = new FileReader("src/test/resources/Issue537.xml");
            expected = XML.toJSONObject(fileReader);
            fileReader.close();
        }

        JSONObject test;
        try{
            test = future.get(2, TimeUnit.SECONDS);
            Files.writeString(Paths.get("milestone5_output.txt"), test.toString());
        } catch (Throwable e) {
            test = new JSONObject();
        }

        assertNotEquals(expected.toString(), test.toString());

    }
}
