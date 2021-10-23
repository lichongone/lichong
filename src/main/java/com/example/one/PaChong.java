package com.example.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaChong {
    private final  static String address="http://www.ip33.com/area_code.html";
    public static void main(String[] args) throws IOException {
        URL url=new URL(address);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestProperty("Accept","*/*");
        httpURLConnection.setRequestProperty("Connection","Keep-Alive");
        httpURLConnection.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        httpURLConnection.connect();
        Map<String, List<String>> header=httpURLConnection.getHeaderFields();
        BufferedReader inputStreamReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
        String stringBuilder=new String();
        String line;
        while ((line=inputStreamReader.readLine())!=null){
            stringBuilder+=line;
        }
        inputStreamReader.close();
        String[] li=stringBuilder.split("</li>");
        List<String> list=Arrays.stream(li).filter(p->p.contains("<li>")).filter(p->!p.contains("<ul>")).map(p->p.split("<li>")).map(p->p[1]).collect(Collectors.toList());
        list.stream().forEach(s -> System.out.println(s+"11111"));


    }

}
