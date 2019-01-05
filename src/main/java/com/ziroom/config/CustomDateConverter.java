package com.ziroom.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            //进行日期转换
            if(source.length()>10){
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
            }else{
                return new SimpleDateFormat("yyyy-MM-dd").parse(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}