package com.wen.love.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.wen.love.mapper.CateMapper;
import com.wen.love.po.Cate;
import com.wen.love.redis.RedisUtil;
import com.wen.love.service.CateService;
import com.wen.love.util.CodeConstant;
import com.wen.love.util.GeneralDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Random;


@Service
public class CateServiceImpl implements CateService {

    @Resource
    private CateMapper mapper;

    @Autowired
    private CateService cateService;

    @Override
    public GeneralDto<Cate> select(Cate cate) {
        GeneralDto<Cate> generalDto = new GeneralDto<>();
        int num;
        try {
            num = cateService.getRandomNum(cate.getType());
            Cate cate1 = JSONObject.parseObject(RedisUtil.get(CodeConstant.CATE_PRE + num),Cate.class);
            System.out.println(cate1);
            while (cate1 == null){
                num = cateService.getRandomNum(cate.getType());
                cate1 = mapper.selectById(num);
                if(cate1 != null){
                    RedisUtil.set(CodeConstant.CATE_PRE + cate1.getId(),JSONObject.toJSONString(cate1));
                }
            }
            generalDto.setItem(cate1);
            generalDto.setRetCode(CodeConstant.SELECT_SUCCESS_CODE);
            generalDto.setRetMsg(CodeConstant.SELECT_SUCCESS_MSG);
        }catch (Exception e){
            System.out.println("菜谱查询异常："+e);
            e.printStackTrace();
        }
        return generalDto;
    }

    @Override
    public GeneralDto insert(Cate cate) {
        GeneralDto generalDto = new GeneralDto();
        try{
            int insert = mapper.insert(cate);
            if(insert == 1){
                RedisUtil.set(CodeConstant.CATE_PRE + cate.getId(),JSONObject.toJSONString(cate));
                generalDto.setItem(insert);
                generalDto.setRetCode(CodeConstant.INSERT_SUCCESS_CODE);
                generalDto.setRetMsg(CodeConstant.INSERT_SUCCESS_MSG);
            }else {
                generalDto.setRetCode(CodeConstant.INSERT_FAIL_CODE);
                generalDto.setRetMsg(CodeConstant.INSERT_FAIL_MSG);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalDto;
    }

    public int getRandomNum(int type){
        Random random = new Random();
        List<Cate> cates = mapper.selectListByType(type);
        int i = random.nextInt(cates.size());
        return cates.get(i).getId();
    }

}
