package com.wen.love.mapper;


import com.wen.love.po.Cate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CateMapper {

    String tableName = "cate";

    String selectPrefix = "SELECT * FROM " + tableName;

    @Select( selectPrefix + " where id = #{id} and type = #{type} ")
    Cate select(Cate cate);


    @Insert(" INSERT INTO " + tableName + "( name,type ) values(#{name},#{type}) ")
    int insert(Cate cate);


    @Select(" Select * from " + tableName + " where type = #{type}" )
    List<Cate> selectListByType(int type);

    @Select( selectPrefix + " where id = #{n} ")
    Cate selectById(int n);

    @Select( selectPrefix + " where name = #{name} and type = #{type} ")
    Cate selectByCate(Cate cate);

}
