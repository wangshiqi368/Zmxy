package com.zhimai.xingyun.util;

import com.github.promeg.pinyinhelper.Pinyin;
import org.springframework.util.StringUtils;

/**
 * 拼音转换工具类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public final class PinyinUtils {

    // Private constructor to prevent instantiation
    private PinyinUtils() {
    }

    /**
     * 将中文字符串转换为其全小写、无空格的拼音表示形式。
     *
     * @param chineseString 中文输入字符串 (例如, "张三").
     * @return 转换后的拼音字符串 (例如, "zhangsan"). 如果输入为空，则返回 null.
     */
    public static String getPinyin(String chineseString) {
        if (!StringUtils.hasText(chineseString)) {
            return null;
        }
        // Pinyin.toPinyin(str, separator) 会将字符串转换为拼音，并用指定分隔符连接
        // 这里我们使用空字符串作为分隔符，实现无缝连接
        return Pinyin.toPinyin(chineseString, "").toLowerCase();
    }
}
