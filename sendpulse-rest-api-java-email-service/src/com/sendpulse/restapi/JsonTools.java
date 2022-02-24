package com.sendpulse.restapi;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonTools {

    public String jsonToString(String path) throws Exception{
        String file = path;
        String json = readFileAsString(file);
        return json;
    }

    public static String readFileAsString(String file)throws Exception{
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
