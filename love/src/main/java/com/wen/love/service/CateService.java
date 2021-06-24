package com.wen.love.service;

import com.wen.love.po.Cate;
import com.wen.love.util.GeneralDto;

public interface CateService {


    GeneralDto<Cate> select(Cate cate);

    GeneralDto insert(Cate cate);



}
