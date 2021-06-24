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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public GeneralDto<Cate> select(Cate cate) {
        GeneralDto<Cate> generalDto = new GeneralDto<>();
        int num;
        Cate cate1 = new Cate();
        try {
            if(cate != null && cate.getType() != 0){
                System.out.println("开始查询菜谱");
                Random random = new Random();
                List<Cate> cates = mapper.selectListByType(cate.getType());
                if(cates != null && cates.size() != 0){
                    num = random.nextInt(cates.size());
                    System.out.println("随机获取到的菜谱id："+num);
                    String s = redisUtil.get(CodeConstant.CATE_PRE + num);
                    if(s != null && !"".equals(s)){
                        System.out.println("缓存中存在菜谱:" + s);
                        cate1 = JSONObject.parseObject(s,Cate.class);
                    }else {
                        do{
                            System.out.println("缓存中不存在该菜谱，从数据库中查询");
                            cate1 = mapper.selectById(num);
                            if(cate1 != null){
                                redisUtil.set(CodeConstant.CATE_PRE + cate1.getId(),JSONObject.toJSONString(cate1));
                            }
                            num = random.nextInt(cates.size());
                        } while (cate1 == null);
                    }
                    System.out.println(cate1);
                    generalDto.setItem(cate1);
                }else {
                    generalDto.setItem("没有该类型的菜谱");
                }
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
                        redisUtil.set(CodeConstant.CATE_PRE + cate.getId(),JSONObject.toJSONString(cate));
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
