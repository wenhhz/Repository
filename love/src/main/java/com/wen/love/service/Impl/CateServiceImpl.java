package com.wen.love.service.Impl;

import com.alibaba.fastjson.JSONArray;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class CateServiceImpl implements CateService {

    @Resource
    private CateMapper mapper;

    @Autowired
    private CateService cateService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public GeneralDto<Cate> select(Cate cate) {
        GeneralDto<Cate> generalDto = new GeneralDto<>();
        int num;
        List<Cate> cates = new ArrayList<>();
        Cate cate1 = new Cate();
        try {
            if(cate != null && cate.getType() != 0){
                System.out.println("开始查询菜谱");
                Random random = new Random();
                String arrays = redisUtil.get(CodeConstant.CATE_PRE + cate.getType());
                cates = JSONObject.parseArray(arrays,Cate.class);
                if(cates == null || cates.size() == 0){
                    System.out.println("缓存中不存在菜谱，从数据库中查找");
                    cates = mapper.selectListByType(cate.getType());
                    redisUtil.set(CodeConstant.CATE_PRE+cate.getType(),JSONObject.toJSONString(cates));
                }
                num = random.nextInt(cates.size());
                System.out.println("随机获取到的菜谱id："+num);
                do{
                    System.out.println("循环获取确保获取到了菜谱");
                    cate1 = cates.get(num);
                    num = random.nextInt(cates.size());
                } while (cate1 == null);
                System.out.println(cate1);
                generalDto.setItem(cate1);
                generalDto.setRetCode(CodeConstant.SELECT_SUCCESS_CODE);
                generalDto.setRetMsg(CodeConstant.SELECT_SUCCESS_MSG);
            }else {
                generalDto.setItem("参数输入错误");
                generalDto.setRetCode(CodeConstant.SELECT_FAIL_CODE);
                generalDto.setRetMsg(CodeConstant.SELECT_FAIL_MSG);
            }
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
            if( cate != null && cate.getName() != null && cate.getType() != 0){
                //判断菜谱是否存在
                Cate cate1 = mapper.selectByCate(cate);
                if(cate1 != null && cate1.getId() != 0 && cate1.getName() != null && cate1.getType() != 0){
                    generalDto.setItem("菜谱已经存在");
                    generalDto.setRetCode(CodeConstant.INSERT_FAIL_CODE);
                    generalDto.setRetMsg(CodeConstant.INSERT_FAIL_MSG);
                }else {
                    int insert = mapper.insert(cate);
                    if(insert == 1){
                        List<Cate> cates = mapper.selectListByType(cate.getType());
                        redisUtil.set(CodeConstant.CATE_PRE + cate.getType(),JSONObject.toJSONString(cates));
                        generalDto.setItem(insert);
                        generalDto.setRetCode(CodeConstant.INSERT_SUCCESS_CODE);
                        generalDto.setRetMsg(CodeConstant.INSERT_SUCCESS_MSG);
                    }else {
                        generalDto.setRetCode(CodeConstant.INSERT_FAIL_CODE);
                        generalDto.setRetMsg(CodeConstant.INSERT_FAIL_MSG);
                    }
                }
            }else {
                generalDto.setItem("参数输入错误");
                generalDto.setRetCode(CodeConstant.INSERT_FAIL_CODE);
                generalDto.setRetMsg(CodeConstant.INSERT_FAIL_MSG);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalDto;
    }


}
