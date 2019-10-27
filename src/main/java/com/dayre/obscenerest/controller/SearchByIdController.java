package com.dayre.obscenerest.controller;

import com.dayre.obscenerest.Application;
import com.dayre.obscenerest.SongResult;
import com.dayre.obscenerest.SongResultDao;
import org.springframework.web.bind.annotation.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

@Named
@RequestScoped
@RestController
public class SearchByIdController {
    @Inject
    private SongResultDao db;

    @RequestMapping("/") //Точка входа
    @ResponseBody
    ArrayList<SongResult> searchById(@RequestParam(value="id") ArrayList<Long> id) {
        ArrayList<SongResult> result = new ArrayList<>();
        for (Long aId : id) {
            if (Application.popularListId.contains(aId)) {
                int n = Application.popularListId.indexOf(aId);
                result.add(new SongResult(Application.popularListId.get(n),Application.popularListTags.get(n)));
            } else {
                SongResult res = db.findById(aId);
                if (res != null) {
                    result.add(res);
                    //Потом, конечно, надо или оставить или удалить, чтобы проверка не замедляла каждый цикл.
                    if (Application.usePopular) {
                        res.setPopularity(res.getPopularity() + 1);
                        db.save(res);
                    }
                } else result.add(new SongResult(aId, "no record found", 0));
                //Может просто пустую строку возвращать, не ясны потребности клиента.
            }
        }
        return result;
    }
}