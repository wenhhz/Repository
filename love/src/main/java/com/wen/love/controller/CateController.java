package com.wen.love.controller;


import com.wen.love.po.Cate;
import com.wen.love.service.CateService;
import com.wen.love.util.GeneralDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/love")
public class CateController {

    @Autowired
    private CateService cateService;


    @RequestMapping("/insert")
    public GeneralDto insert(Cate cate){
        System.out.println("插入菜谱: " + cate);
        GeneralDto generalDto = new GeneralDto();
        generalDto = cateService.insert(cate);
        return generalDto;
    }



    @RequestMapping("/select")
    public GeneralDto<Cate> select(Cate cate){
        System.out.println("查询菜谱: " + cate);
        GeneralDto<Cate> generalDto = new GeneralDto<>();
        generalDto = cateService.select(cate);
        return generalDto;
    }
}
