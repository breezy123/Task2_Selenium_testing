package Absa_Web_UI_test;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;

public class TestMain {

    public static void main(String[] args){
        //System.out.println(generateText());

        String lineOfCurrencies = "USD JPY AUD SGD HKD CAD CHF GBP EURO INR";
        String[] currencies = lineOfCurrencies.split(" ");


        System.out.println("input string words separated by whitespace: " + lineOfCurrencies);

        System.out.println("output string: " + currencies[0]+"\t"+currencies[1]+"\t"+currencies[2]+"\t"+currencies[3]);
    }

    public static String  generateText(){

        String generatedString;
        return  generatedString = RandomStringUtils.randomAlphabetic(5);
    }
}
