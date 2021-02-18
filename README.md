
# swe262p-milestone4

Add streaming methods to the library that allow the client code to chain operations without having to load the entire file in memory. For example:

## XML File Access:
https://drive.google.com/drive/folders/1LAK662tWXdfgcXJL8UJxIFjB2Jwumi4T?usp=sharing
download and add the xml files into the main directory of this repo.

## Unit tests

The unit tests are in the Milestone4.java under json\src\test\java\org\json\junit.
The test cases all use the "books.xml" as the implemented object.


# swe262p-milestone3

this milestone project overloads the toJSONObject method in XML.java to transform the keys with a Function while parsing the XML file into a jsonObject.

This toJSONObject will boost the performance comparing to the key transformer function we have done in milestone 1 since in milestone 1 we needed to parse the XML file and convert into a jsonObject first then go into the jsonObject to put the values and new keys into a new jsonObject. Here we can finish the key transformation during parsing, which saves the time to look up the keys and reconstruct the jsonObject.

## XML File Access:
https://drive.google.com/drive/folders/1LAK662tWXdfgcXJL8UJxIFjB2Jwumi4T?usp=sharing

## Unit tests
  the unit tests are written in the Milestone3Test.java, under json\src\test\java\org\json\junit.
  The test cases all use the "books.xml" as the xml file to be transformed.
  To make the tests run as expected, place the "books.xml", which can be found in https://drive.google.com/drive/folders/1LAK662tWXdfgcXJL8UJxIFjB2Jwumi4T?usp=sharing to the JSON-java directory.

  each test case consists of a keyTransformer class that implements Function<String,String>. 

  the keyTranformer in test case 1 transforms the keys by adding prefix "swe262_" ie. foo->swe262_foo
  the keyTransformer in test case 2 transforms the keys by reversing the keys. ie. foo->oof
  the keyTransformer in test case 3 transforms the keys by adding ** surrounds the keys. ie. foo->**foo** 

## Modifications to the Library Code inside XML.java
1. created
    private static boolean parse3(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config, Function<String,String> keyTransformer)

this method takes the original parse signature + a Function keyTransformer as input and apply the keyTransformer whenever the tagName with its value is about to be accumulated into the context jsonObject. 

2. created 
public static JSONObject toJSONObject(Reader reader, Function<String,String> keyTransformer) 

this method calls the parse3() in a while loop and stops when it reaches the end of the xml content then returns the context jsonObject that is built during the parsing.










# swe262p-milestone2

## XML File Access:
https://drive.google.com/drive/folders/1LAK662tWXdfgcXJL8UJxIFjB2Jwumi4T?usp=sharing

## Unit tests
  the unit tests are written in the SWE262pTest.java, under \src\test\java\org\json\junit
  
## Modifications to the Library Code inside XML.java
  1. JSONObject Parse1(XMLTokener x, JSONObject context, String name, ListIterator pointer, XMLParserConfiguration config)
      This method is intent to skip tokens until the token is equal to the last key (or item) in the ListIterator pointer.
      uses XMLTokener skipPast("<") to skip tokens and check if tagName (the next token after every "<")= pointer.next().
      When token==last key of the poniter, in a while loop call parse2 to parse/extract the last key element.
  
  2. Boolean parse2(XMLTokener x, JSONObject context, String name, String key, XMLParserConfiguration config)
    This recursive method is intent to parse the token and accumulate objects into JsonObject context. 
    Different from the original parse(), this method compares the current tagName with the key, which is the last key of the pointer, when a closing tag (the token after SLASH) is found, and throw a CloseTagFoundException to stop the recursive call.
    
  3. JSONObject toJSONObject(Reader reader, JSONPointer path)
    This overloaded method is intent to use the JSONPointer to extract and return a sub object of a XML file by calling parse1().
    To parse/extract sub object efficiently from large XML, this method utilizes the two new added parse1, parse2 methods to stop parsing when the queried object is extracted, instead of parsing the whole XML file and convert into a JSONObject.
  
  4. JSONObject toJSONObject(Reader reader, JSONPointer path, JSONObject replacement)
    This overloaded method's logic is similar to the toJSONObject(Reader reader, JSONPointer path) above, except that the replacement JSONObject is being cleared and replaced with the sub object extracted from the parse1 method.
  
  5. class CloseTagFoundException, which will be thrown when the last key of the pointer is queried inside parse2 method.
  
