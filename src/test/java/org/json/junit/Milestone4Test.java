package org.json.junit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.Assert.*;


public class Milestone4Test {



   @Test
   //gets the agency value of the second collaborator
   public void test1() throws IOException {
       FileReader fileReader=new FileReader("src/test/resources/Issue537.xml");
       JSONObject expected= XML.toJSONObject(fileReader);
       String expected_value = (String)expected.query("/clinical_study/sponsors/collaborator/1/agency");
       fileReader.close();

       fileReader=new FileReader("src/test/resources/Issue537.xml");
       List<String> test_value = new ArrayList<>();
       expected.toStream().forEach(node->{
           if(((HashMap) node).get("/clinical_study/sponsors/collaborator/1/agency")!=null){
                test_value.add((String) new ArrayList<Object>(((HashMap)node).values()).get(0));
           }
       });
       fileReader.close();
       if(test_value.size()==0) assertEquals(expected_value,test_value.get(0));
   }

    @Test
    //return a list of all titles from stream
    public void test2() throws IOException {
        FileReader fileReader=new FileReader("books.xml");
        JSONObject expected= XML.toJSONObject(fileReader);
        List<Object> expected_titles = new ArrayList<>();
        JSONArray books = (JSONArray) (expected.optQuery("/catalog/book"));
        for(Object book:books.toList()){
            expected_titles.add(((HashMap)book).get("title"));
        }
        fileReader.close();

        fileReader=new FileReader("books.xml");
        List<Object> test_titles = expected.toStream()
                .filter(node->((HashMap) node).keySet().iterator().next().toString().contains("/catalog/book"))
                .filter(node->((HashMap) node).keySet().iterator().next().toString().contains("/title"))
                .map(node-> new ArrayList<Object>(((HashMap)node).values()).get(0)
        ).collect(Collectors.toList());
        fileReader.close();

        assertEquals(expected_titles.toString(),test_titles.toString());
    }


    @Test
    //test on the length of the jsonobject in the first collaborator
    public void test3() throws IOException {
        FileReader fileReader=new FileReader("src/test/resources/Issue537.xml");
        JSONObject expected= XML.toJSONObject(fileReader);
        int expected_count = ((JSONObject)(expected.query("/clinical_study/sponsors/collaborator/0"))).length();
        fileReader.close();

        fileReader=new FileReader("src/test/resources/Issue537.xml");
        int test_count = (int)expected.toStream()
        .filter(node->((HashMap) node).keySet().iterator().next().toString().contains("/clinical_study/sponsors/collaborator/0"))
        .count();
        fileReader.close();

        assertEquals(expected_count,test_count);
    }
}
