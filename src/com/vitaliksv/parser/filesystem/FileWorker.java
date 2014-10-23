package com.vitaliksv.parser.filesystem;

import java.io.*;
import java.util.ArrayList;


public class FileWorker {

    public static void write(String fileName, String text) {

        File file = new File(fileName);

        try {

            if(!file.exists()){
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.print(text);
            } finally {

                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> read(String fileName) throws FileNotFoundException {

        final ArrayList<String> messageList = new ArrayList<>();

        File file = new File(fileName);

        exists(fileName);

        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    messageList.add(s);
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return messageList;
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }
}
