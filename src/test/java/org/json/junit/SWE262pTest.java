package org.json.junit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.XML;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class SWE262pTest {
    @Test
    //small dataset
    public void taskTwoTest1() throws IOException {
        //Given
        FileReader fileReader=new FileReader(new File("books.xml"));
        JSONPointer jsonPointer=new JSONPointer("/catalog/book/1/author");
        JSONObject expected=new JSONObject();
        expected.accumulate("author", XML.toJSONObject(fileReader).query(jsonPointer));
        fileReader.close();
        fileReader=new FileReader(new File("books.xml"));
        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer);
        fileReader.close();
        //THEN
//        System.out.println(test);
        assertEquals(expected.toString(),test.toString());

    }

    @Test
//    large dataset >1GB, return {"sitename":"Wikipedia"}
    public void taskTwoTest2() throws IOException {
        //Given
        FileReader fileReader=new FileReader(new File("enwiki-20201220-stub-articles24.xml"));
        JSONPointer jsonPointer=new JSONPointer("/mediawiki/siteinfo/sitename");
        JSONObject expected=new JSONObject().put("sitename","Wikipedia");

        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer);
//        System.out.println(test);
        //THEN
        assertEquals(expected.toString(),test.toString());

        fileReader.close();
    }

    @Test
//     invalid json pointer return null
    public void taskTwoTest3() throws IOException {
        //Given
        FileReader fileReader=new FileReader(new File("books.xml"));
        JSONPointer jsonPointer=new JSONPointer("/book/1/author");
        Object expected=XML.toJSONObject(fileReader).optQuery(jsonPointer);
        fileReader.close();
        fileReader=new FileReader(new File("books.xml"));
        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer);

        //THEN
        assertEquals(expected,test);
        assertTrue(expected==null);
        fileReader.close();
    }




    @Test
    //empty json pointer return whole xml file as a jsonObject
    public void taskTwoTest4() throws IOException {
        //Given
        FileReader fileReader=new FileReader(new File("books.xml"));
        JSONPointer jsonPointer=new JSONPointer("");
        JSONObject expected=XML.toJSONObject(fileReader);
        fileReader.close();
        fileReader=new FileReader(new File("books.xml"));
        //WHEN
        Object test= XML.toJSONObject(fileReader,jsonPointer);
        //THEN
        assertEquals(expected.toString(),test.toString());
        assertFalse(test==null);
        fileReader.close();

    }

    /**

     **   ************starting to test replacement method************
     **/

    @Test
    //replacement is empty JsonObject, replace the whole xml file as a jsonObject
    public void taskFiveTest1() throws IOException {
        //Given
        FileReader fileReader=new FileReader("books.xml");
        JSONPointer jsonPointer=new JSONPointer("");
        JSONObject replacement= new JSONObject();
        JSONObject expected=XML.toJSONObject(fileReader);
        fileReader.close();
        fileReader=new FileReader("books.xml");
        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer,replacement);
        //THEN
        assertEquals(expected.toString(),test.toString());
        assertFalse(test==null);
        assertFalse(replacement==null);
        assertTrue(replacement.isEmpty());
        fileReader.close();
    }

    @Test
    //replacement is not empty JsonObject and queried object is a string {"author":"Gambardella, Matthew"}
    public void taskFiveTest2() throws IOException {
        //Given
        FileReader fileReader=new FileReader("books.xml");
        JSONPointer jsonPointer=new JSONPointer("/catalog/book/0/author");
        JSONObject replacement= new JSONObject().accumulate("this","[]");
        JSONObject expected=XML.toJSONObject(fileReader, jsonPointer);
        fileReader.close();
        fileReader=new FileReader("books.xml");
        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer,replacement);
        //THEN
        assertEquals(expected.toString(),test.toString());
        fileReader.close();
    }

    @Test
    //file is >1gb, replacement is not empty jsonObject, and queried object is a nested JOSNArray
    public void taskFiveTest3() throws IOException {
        //Given
        FileReader fileReader=new FileReader("enwiki-20201220-stub-articles24.xml");

        JSONPointer jsonPointer=new JSONPointer("/mediawiki/siteinfo/namespaces");

        JSONObject replacement= new JSONObject().accumulate("this","{empty}");

//        JSONObject expected=new JSONObject().accumulate("namespace",
//                "[{\"key\":-2,\"case\":\"first-letter\",\"content\":\"Media\"}," +
//            "{\"key\":-1,\"case\":\"first-letter\",\"content\":\"Special\"}," +
//            "{\"key\":0,\"case\":\"first-letter\"}," +
//                        "{\"key\":1,\"case\":\"first-letter\",\"content\":\"Talk\"}," +
//            "{\"key\":2,\"case\":\"first-letter\",\"content\":\"User\"}," +
//            "{\"key\":3,\"case\":\"first-letter\",\"content\":\"User talk\"}," +
//                "{\"key\":4,\"case\":\"first-letter\",\"content\":\"Wikipedia\"}," +
//            "{\"key\":5,\"case\":\"first-letter\",\"content\":\"Wikipedia talk\"}," +
//                "{\"key\":6,\"case\":\"first-letter\",\"content\":\"File\"}," +
//            "{\"key\":7,\"case\":\"first-letter\",\"content\":\"File talk\"}," +
//                "{\"key\":8,\"case\":\"first-letter\",\"content\":\"MediaWiki\"}," +
//            "{\"key\":9,\"case\":\"first-letter\",\"content\":\"MediaWiki talk\"}," +
//                "{\"key\":10,\"case\":\"first-letter\",\"content\":\"Template\"}," +
//            "{\"key\":11,\"case\":\"first-letter\",\"content\":\"Template talk\"}," +
//                "{\"key\":12,\"case\":\"first-letter\",\"content\":\"Help\"}," +
//            "{\"key\":13,\"case\":\"first-letter\",\"content\":\"Help talk\"}," +
//                "{\"key\":14,\"case\":\"first-letter\",\"content\":\"Category\"}," +
//            "{\"key\":15,\"case\":\"first-letter\",\"content\":\"Category talk\"}," +
//                "{\"key\":100,\"case\":\"first-letter\",\"content\":\"Portal\"}," +
//            "{\"key\":101,\"case\":\"first-letter\",\"content\":\"Portal talk\"}," +
//                "{\"key\":108,\"case\":\"first-letter\",\"content\":\"Book\"}," +
//            "{\"key\":109,\"case\":\"first-letter\",\"content\":\"Book talk\"}," +
//                "{\"key\":118,\"case\":\"first-letter\",\"content\":\"Draft\"}," +
//            "{\"key\":119,\"case\":\"first-letter\",\"content\":\"Draft talk\"}," +
//                "{\"key\":446,\"case\":\"first-letter\",\"content\":\"Education Program\"}," +
//                "{\"key\":447,\"case\":\"first-letter\",\"content\":\"Education Program talk\"}," +
//            "{\"key\":710,\"case\":\"first-letter\",\"content\":\"TimedText\"}," +
//            "{\"key\":711,\"case\":\"first-letter\",\"content\":\"TimedText talk\"}," +
//                "{\"key\":828,\"case\":\"first-letter\",\"content\":\"Module\"}," +
//            "{\"key\":829,\"case\":\"first-letter\",\"content\":\"Module talk\"}," +
//                "{\"key\":2300,\"case\":\"first-letter\",\"content\":\"Gadget\"}" +
//            ",{\"key\":2301,\"case\":\"first-letter\",\"content\":\"Gadget talk\"}," +
//                "{\"key\":2302,\"case\":\"case-sensitive\",\"content\":\"Gadget definition\"}," +
//                "{\"key\":2303,\"case\":\"case-sensitive\",\"content\":\"Gadget definition talk\"}]");



        //WHEN
        assertFalse(replacement.isEmpty());
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer,replacement);
//        System.out.println(test);

        //THEN
        JSONArray arr=(JSONArray) test.get("namespace");
        assertEquals(-2,((JSONObject)arr.get(0)).get("key"));
        assertEquals(-1,((JSONObject)arr.get(1)).get("key"));
        assertEquals("first-letter",((JSONObject)arr.get(0)).get("case"));
        assertTrue(replacement.isEmpty());
        fileReader.close();

    }

    @Test
    //queried invalid pointer
    public void taskFiveTest4() throws IOException {
        //Given
        FileReader fileReader=new FileReader("enwiki-20201220-stub-articles24.xmL");

        JSONPointer jsonPointer=new JSONPointer("/mediawio");

        JSONObject replacement= new JSONObject().accumulate("this","{empty}");

        //WHEN
        assertFalse(replacement.isEmpty());
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer,replacement);

        //THEN
        assertEquals(null,test);
        assertTrue(replacement.isEmpty());
        fileReader.close();
    }

    @Test
    //JsonPointer has length of 1.
    public void taskFiveTest5() throws IOException {
        //Given
        FileReader fileReader=new FileReader("books.xml");

        JSONPointer jsonPointer=new JSONPointer("/catalog");

        JSONObject replacement= new JSONObject().accumulate("this","[]");

        JSONObject expected=(JSONObject) XML.toJSONObject(fileReader).query("/catalog");

        fileReader.close();
        fileReader=new FileReader("books.xml");
        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,jsonPointer,replacement);
        //THEN
        assertEquals(expected.toString(),test.toString());
        fileReader.close();
    }



}
