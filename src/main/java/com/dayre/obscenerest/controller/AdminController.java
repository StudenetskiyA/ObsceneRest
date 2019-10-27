package com.dayre.obscenerest.controller;

import com.dayre.obscenerest.Application;
import com.dayre.obscenerest.SongResult;
import com.dayre.obscenerest.SongResultDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static com.dayre.obscenerest.Application.getListFromString;

@Named
@RequestScoped
@RestController
public class AdminController {
    @Inject
    private SongResultDao db;

//    @RequestMapping("/start") //Точка входа
//    @ResponseBody
//    public static String start() {
//        Application.db = new SongResultDao();
//        //Популярные треки сразу в память.
//        if (Application.usePopular) {
//            //Здесь надо не первые n треков, а первые n по популярности.
//            //Но тесты особого прироста скорости не показывали, поэтому пока заморозим так.
//            for (int i = 0; i < Application.popularListSize; i++) {
//                Application.popularListId.add((long) i);
//                SongResult sr = Application.db.findById(i);
//                if (sr!=null)
//                Application.popularListTags.add(sr.getTags());
//                else
//                    Application.popularListTags.add("");
//            }
//        }
//        return "Service started.";
//    }
//    @RequestMapping("/stop") //Точка входа
//    @ResponseBody
//    static String stop() {
//        Application.db.close();
//        return "Service stopped.";
//    }

    @RequestMapping("/help") //Точка входа
    @ResponseBody
    public String help() {
        return "Obscene service.<br>" +
                "Commands:<br>" +
                "1. /help - shows this page.<br>" +
                "2. ?id=N,N1... - search tags for id.<br>" +
                "3. /edit/?id=N,N1... - page for editing tags for id.<br>" +
                "4. /save/?id=N&tags=TAG1,TAG2... - edit single id.";
    }

    @RequestMapping("/test") //Точка входа
    @ResponseBody
    SongResult test() {
        SongResult sr = db.findById(0l);
        return sr;
    }

    @RequestMapping("/save") //Точка входа
    @ResponseBody
    String save(@RequestParam(value="id") Long id, @RequestParam(value="tags") String tags) {
        System.out.println("save = "+id.toString());
        int recordSaved = 0;
        SongResult track = db.findById(id);
        long popularity = 0L;
        if (track!=null) popularity = track.getPopularity();
        db.save(new SongResult(id,tags,popularity));
        recordSaved++;
        return "Records saved "+recordSaved+".";
    }

    @RequestMapping("/edit") //Точка входа
    @ResponseBody
    String edit(@RequestParam(value="id") ArrayList<Long> id) {
        //Запись с id=0 хранит все возможные теги, очень удобно получается.
        StringBuilder totalPage = new StringBuilder();
        int i=0;
        for (Long aLong : id) {
            SongResult res = db.findById(aLong);
            if (res!=null)
                totalPage.append(generateHTMLForIdAndTags(i, aLong.toString(), res.getTags()));
            else totalPage.append(generateHTMLForIdAndTags(i, aLong.toString(), ""));
            i++;
        }
        SongResult res = db.findById(0);
        String alltag = res.getTags();
        String topElement = "<script type=\"text/javascript\">\n" +
                "function addTag()\n" +
                "{\n" +
                "\tvar url = \"save/?id=0&tags="+alltag+", \"+document.getElementById(\"addTag\").value;\n" +
                "\tvar a = window.open(url,\"_blank\", \"\"); \n" +
                "    a.blur();\n" +
                "    window.location.reload(true);\n" +
                "return false;\n" +
                "}\n" +
                "</script>\n" + "Очень удобно нажимать кнопки с Ctrl.<br>" +
                "Текущие теги: "+alltag +"<br>\n" +
                "Добавить тег:\n" +
                "<input type=\"text\" id=\"addTag\" value=\"\">\n" +
                "<button onclick = \"addTag()\">Добавить.</button>\n" +
                "<br><br>";

        return Application.htmlHeader + topElement + totalPage + Application.htmlBottom;
    }

    private String generateHTMLForIdAndTags(int cycle, String id, String tags) {
        String names = "id = " + id;
        String script = "<script type=\"text/javascript\">\n" +
                "function save"+cycle+"()\n" +
                "{\n" +
                "\tvar url = \"save/?id="+id+"&tags=\"+document.getElementById(\"tags"+cycle+"\").value;\n" +
                "\tvar a = window.open(url,\"_blank\", \"\"); \n" +
                "    a.blur();\n" +
                "    window.location.reload(true);\n" +
                "return false;\n" +
                "}\n" +
                "</script>\n" +
                "<script type=\"text/javascript\">\n" +
                "function addTo"+cycle+"()\n" +
                "{\n" +
                "\tdocument.getElementById(\"tags"+cycle+"\").value+=document.getElementById(\"list"+cycle+"\").value + \",\";\n" +
                "return false;\n" +
                "}\n" +
                "</script>";

        String buttons = "<button onclick = \"addTo"+cycle+"()\">Add</button>\n" +
                "<input type=\"text\" id=\"tags"+cycle+"\" value=\""+tags+"\">\n" +
                "<button onclick = \"save"+cycle+"()\">Сохранить</button>";
        SongResult res = db.findById(0);
        String alltag = res.getTags();
        String selector = generateListFromTags(alltag,cycle);
        return names + selector + buttons + script+ "<br>";
    }

    private static String generateListFromTags(String tags, int cycle) {
        List<String> list = getListFromString(tags);
        StringBuilder result = new StringBuilder("<select name=\"town\" id=\"list" + cycle + "\">");
        for (String s : list) {
            result.append("<option value=\"").append(s).append("\" >").append(s).append("</option>");
        }
        result.append("</select>");
        return result.toString();
    }
}
