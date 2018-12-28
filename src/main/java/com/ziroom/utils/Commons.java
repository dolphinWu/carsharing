package com.ziroom.utils;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 公共函数
 */
@Component
public class Commons {

    /**
     * 文件上传，为文件重新命名
     **/
    public static String getFileRename(String name) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String sdfDate = sdf.format(date);
        int pos = name.lastIndexOf(".");
        String suffix = name.substring(pos);
        String rename = sdfDate+suffix;
        return rename;
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    /**
     * 英文格式的日期
     * @param unixTime
     * @return
     */
    public static String fmtdate_en(Integer unixTime){
        String fmtdate = fmtdate(unixTime, "d,MMM,yyyy");
        String[] dateArr = fmtdate.split(",");
        String rs = "<span>" + dateArr[0] + "</span> " + dateArr[1] + "  " + dateArr[2];
        return rs;
    }


    /**
     * 英文格式的日期-月，日
     * @param unixTime
     * @return
     */
    public static String fmtdate_en_m(Integer unixTime){
        return fmtdate(unixTime,"MMM d");
    }

    /**
     * 日期-年
     * @param unixTime
     * @return
     */
    public static String fmtdate_en_y(Integer unixTime){
        return fmtdate(unixTime,"yyyy");
    }

    /**
     * 将中文的yyyy年MM月 - > yyyy
     * @param date
     * @return
     */
    public static String parsedate_zh_y_m(String date){
        if (StringUtils.isNotBlank(date)){
            Date d = DateKit.dateFormat(date, "yyyy年MM月");
            return DateKit.dateFormat(d, "yyyy");
        }
        return null;
    }

    /**
     * 字符串转Date
     * @param date
     * @return
     */
    public static Date fmtdate_date(String date){
        if (StringUtils.isNotBlank(date)){
            return DateKit.dateFormat(date, "yyyy年MM月");
        }
        return null;
    }

    /**
     * 根据nuix时间戳获取Date
     * @param nuixTime
     * @return
     */
    public static Date fmtdate_unxtime(Integer nuixTime){
        if (null != nuixTime){
            return DateKit.getDateByUnixTime(nuixTime);
        }
        return  null;
    }

    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return com.ziroom.utils.UUID.random(1, max) + str;
    }

    public static String random(Long seed, int max, String str){
        if (seed == null)
            return random(max, str);
        Random random = new Random(seed);
        return random.nextInt(max) + str;
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }


}
