package com.vitaliksv.parser.app;

import com.vitaliksv.parser.filesystem.FileWorker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Application {

    final int TOP_MOST_POPULAR_COUNT = 3;

    private String pathIn;
    private String pathOut;

    private HashMap<String, Integer> types;
    private Set<String> messageTypes;
    private ArrayList<LogMessage> messageList;

    private HashMap<String, HashMap<String, Integer>> messageByTypeCounter;
    private HashMap<String, Integer> messageByType;
    private ArrayList<String> messages;

    private StringBuilder sb;

    public Application(String in, String out){

        this.pathIn = in;
        this.pathOut = out;
    }

    public void startApplication()  throws Exception{

        types = new HashMap<String, Integer>();
        messageTypes = new HashSet<String>();
        messageList = new ArrayList<LogMessage>();
        messageByTypeCounter = new HashMap<String, HashMap<String, Integer>>();
        messageByType = new HashMap<String, Integer>();

        messages = FileWorker.read(pathIn);
        messageSplit(messages);

        sb = new StringBuilder();


        for(int i = 0; i < messageList.size(); i++){
            messageTypes.add(messageList.get(i).getType());
        }

        for( String entry : messageTypes ){

            types.put(entry, 0);
        }

        int typeMessageCounter;
        String typeTmp;
        for(int i = 0; i < messageList.size(); i++){

            typeTmp = messageList.get(i).getType();
            typeMessageCounter = types.get(typeTmp);
            typeMessageCounter++;
            types.put(typeTmp, typeMessageCounter);
        }

        for( String entry : messageTypes ){

            System.out.println(entry + ": " + types.get(entry));
            sb.append(entry + ": " + types.get(entry));
            sb.append("\n");
        }

        ArrayList<HashMap<String, Integer>> ttt = new ArrayList<HashMap<String, Integer>>();

        for( String entry : messageTypes ){
            messageByTypeCounter.put(entry, new HashMap<String, Integer>());

            messageByType.clear();
            for(int i = 0; i < messageList.size(); i++){

                if(messageList.get(i).getType().equals(entry)){

                    if (messageByTypeCounter.get(entry).containsKey((messageList.get(i).getText()))){

                        typeMessageCounter = messageByTypeCounter.get(entry).get(messageList.get(i).getText());
                        typeMessageCounter++;
                        messageByTypeCounter.get(entry).put(messageList.get(i).getText(), typeMessageCounter);
                    } else {

                        messageByTypeCounter.get(entry).put(messageList.get(i).getText(), 1);
                    }
                }
            }
        }


        int messageCountToShow;
        for( String entry : messageTypes ){

            List entryList = new ArrayList(messageByTypeCounter.get(entry).entrySet());
            Collections.sort(entryList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Map.Entry e1 = (Map.Entry) o1;
                    Map.Entry e2 = (Map.Entry) o2;
                    Comparable c1 = (Comparable) e1.getValue();
                    Comparable c2 = (Comparable) e2.getValue();
                    return c1.compareTo(c2);
                }
            });

            if(entryList.size()>=TOP_MOST_POPULAR_COUNT){
                messageCountToShow = TOP_MOST_POPULAR_COUNT;
            }
            else{
                messageCountToShow = entryList.size();
            }

            System.out.println("");
            sb.append("\n");
            System.out.println(entry + ":");
            sb.append(entry + ":");
            sb.append("\n");
            for(int i = 0; i < messageCountToShow; i++){
                System.out.println(entryList.get((entryList.size())-1-i));
                sb.append(entryList.get((entryList.size())-1-i).toString());
                sb.append("\n");
            }
        }
        FileWorker.write(pathOut, sb.toString());
    }


    private static final String SENTENCE_REGULAR_EXPRESSION =  "((^\\[.*\\])([^:]*):(.*$))";
    private static final Pattern SENTENCE_PATTERN = Pattern.compile(SENTENCE_REGULAR_EXPRESSION);

    private static ArrayList<String> parseSentences(final String text) {
        final Matcher matcher = SENTENCE_PATTERN.matcher(text);
        final ArrayList<String> result = new ArrayList<>();
        if (matcher.matches()) {
            result.add(matcher.group(2).trim());
            result.add(matcher.group(3).trim());
            result.add(matcher.group(4).trim());
        }
        return result;
    }


    private void messageSplit(ArrayList<String> array){

        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < array.size(); i++){
            result = parseSentences(array.get(i));
            messageList.add(new LogMessage(result.get(0), result.get(1), result.get(2)));
        }
    }
}