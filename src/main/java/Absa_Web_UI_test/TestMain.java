package Absa_Web_UI_test;

import org.apache.commons.lang3.RandomStringUtils;

public class TestMain {

    public static void main(String[] args){
        System.out.println(generateText());
    }

    public static String  generateText(){

        String generatedString;
        return  generatedString = RandomStringUtils.randomAlphabetic(5);
    }
}
