package org.json.junit;
import org.junit.Test;


import org.json.JSONObject;
import org.json.XML;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.*;

public class Milestone3Test {




    @Test
    public void taskFourTest1() throws IOException {
        //Given
        class SWE262Prefix implements Function<String,String> {

            @Override
            public String apply(String s) {
                return  "swe262_" + s;
            }
        }

        FileReader fileReader=new FileReader("books.xml");

        JSONObject expected=XML.toJSONObject(fileReader);
        fileReader.close();
        fileReader=new FileReader("books.xml");

        Function SWE262Prefix=new SWE262Prefix();


        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,SWE262Prefix);
        fileReader.close();


        //THEN
        assertFalse(expected.keySet()==test.keySet());

        assertFalse(expected.get("catalog").toString()==test.get("swe262_catalog").toString());

        assertEquals(expected.query("/catalog/book/0/author").toString(),
                test.query("/swe262_catalog/swe262_book/0/swe262_author").toString());

        assertEquals(expected.query("/catalog/book/1/title").toString(),
                test.query("/swe262_catalog/swe262_book/1/swe262_title").toString()
                );


    }

    @Test
    public void taskFourTest2() throws IOException {
        //Given
        class reverseKey implements Function<String,String> {

            @Override
            public String apply(String s) {
                StringBuilder sb=new StringBuilder();
                sb.append(s);
                return sb.reverse().toString();
            }
        }

        FileReader fileReader=new FileReader("books.xml");

        JSONObject expected=XML.toJSONObject(fileReader);
        fileReader.close();
        fileReader=new FileReader("books.xml");

        Function reverseKey=new reverseKey();


        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,reverseKey);
        fileReader.close();


        //THEN
        assertFalse(expected.keySet()==test.keySet());

        assertFalse(expected.get("catalog").toString()==test.get("golatac").toString());

        assertEquals(test.query("/golatac/koob/5/di").toString(),
                expected.query("/catalog/book/5/id").toString());

        assertEquals(test.query("/golatac/koob/6/ecirp").toString(),
                expected.query("/catalog/book/6/price").toString());

    }


    @Test
    public void taskFourTest3() throws IOException {
        //Given
        class asteriskSurround implements Function<String,String> {

            @Override
            public String apply(String s) {
                return "**"+s+"**";
            }
        }

        FileReader fileReader=new FileReader("books.xml");

        JSONObject expected=XML.toJSONObject(fileReader);
        fileReader.close();
        fileReader=new FileReader("books.xml");

        Function asteriskSurround=new asteriskSurround();


        //WHEN
        JSONObject test= XML.toJSONObject(fileReader,asteriskSurround);
        fileReader.close();


        //THEN
        assertFalse(expected.keySet()==test.keySet());

        assertFalse(expected.get("catalog").toString()==test.get("**catalog**").toString());

        assertEquals(test.query("/**catalog**/**book**/3/**author**").toString(),
                expected.query("/catalog/book/3/author").toString());

        assertEquals(test.query("/**catalog**/**book**/4/**title**").toString(),
                expected.query("/catalog/book/4/title").toString());

    }

}
