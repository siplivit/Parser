package com.vitaliksv.parser.app;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private static final String pathIn = "D:\\agileengine.log";

    private static final String pathOut = "D:\\agileengine_result.log";

    public static void main(String[] args) throws Exception{

        File file = new File(pathIn);
        if (! file.exists()) {
            System.out.println("File with name "+ pathIn +" not found");
        }
        else{
            Application myApp = new Application(pathIn, pathOut);
            myApp.startApplication();
        }
    }
}