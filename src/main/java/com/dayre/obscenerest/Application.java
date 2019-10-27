package com.dayre.obscenerest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {
    //Адрес БД, логин, пароль и т.д. в hibernate.cfg.xml
    public static String htmlHeader = "<!DOCTYPE HTML>\n" + "<html>\n" +
            "<head>\n" + "<title>Редактирование тегов</title>\n" +
            "<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\" />\n" +
            "</head>\n" + "<body>";
    public static String htmlBottom = "</body>\n" + "</html>";
   // public static SongResultDao db;

    //Для ускорения популярных запросов
    public static final Boolean usePopular = false;
    //Тесты показали прирост скорости где-то 5%(в пределах погрешности), пока список популярных не используем.
    public static final int popularListSize = 1000;
    public static ArrayList<Long> popularListId = new ArrayList<>();
    public static ArrayList<String> popularListTags = new ArrayList<>();
    public static ArrayList<Long> popularListPop = new ArrayList<>();
    //

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Utils
    public static List<String> getListFromString(String text)  {
        String[] splited  =text.split(",");
        List<String> trimed = Arrays.asList(splited);
        for (int i=0;i<trimed.size();i++) {
            trimed.set(i,trimed.get(i).trim());
        }

        return Arrays.asList(splited);
    }
}