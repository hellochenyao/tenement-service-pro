package com.kanata.user.framework.util;


/**
 * Created by mumu on 2019/3/27.
 */
public class EmojiFilterUtils {

    /**
     * emoji表情替换 ""
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        return EmojiFilter.filterEmoji(source, "");
    }

    /**
     * emoji表情替换#{slipStr}
     *
     * @param source  原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source, String slipStr) {
        if (source != null && !"".equals(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }
}
