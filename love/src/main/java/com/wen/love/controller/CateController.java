package com.wen.love.controller;


import com.wen.love.po.Cate;
import com.wen.love.service.CateService;
import com.wen.love.util.GeneralDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/love")
public class CateController {

    private Logger logger = LoggerFactory.getLogger(CateController.class);


    @Autowired
    private CateService cateService;


    @RequestMapping("/insert")
    public GeneralDto insert(Cate cate){
        logger.info("插入菜谱: " + cate);
        GeneralDto generalDto = new GeneralDto();
        generalDto = cateService.insert(cate);
        return generalDto;
    }



    @RequestMapping("/select")
    public GeneralDto<Cate> select(Cate cate){
        logger.info("查询菜谱: " + cate);
        GeneralDto<Cate> generalDto = new GeneralDto<>();
        generalDto = cateService.select(cate);
        return generalDto;
    }
}
