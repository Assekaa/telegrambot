package com.AITU;

public class StringUtils {

    public static String convertToSlavicLetters(String source){

//        eng to russ
        source = source.replace("T", "Т");
        source = source.replace("P", "П");
        source = source.replace("R", "Р");
        source = source.replace("I", "І");
        source = source.replace("D", "Д");
        source = source.replace("Z", "З");
        source = source.replace("K", "К");
        source = source.replace("N", "Н");
        source = source.replace("V", "У");
//        source = source.replace("teh", "тех.");
        source = source.replace("_", "-");
        source = source.replace("--", " -");
//        source = source.replace("-тех.", " тех.");
        return source;
    }

    public static String convertToEngLetters(String source){

//        eng to russ
        source = source.replace("Т", "T");
        source = source.replace("П", "P");
        source = source.replace("Р", "R");
        source = source.replace("І", "I");
        source = source.replace("Д", "D");
        source = source.replace("З", "Z");
        source = source.replace("К", "K");
        source = source.replace("Н", "N");
        source = source.replace("У", "V");
//        source = source.replace("тех", "teh");
//        source = source.replace(" ", "_");
        return source;
    }
}
