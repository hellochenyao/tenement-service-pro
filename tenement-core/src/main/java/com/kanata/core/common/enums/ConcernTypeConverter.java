package com.kanata.core.common.enums;

import org.springframework.core.convert.converter.Converter;

/**
 * @author chenyao
 * date 2020-09-07
 */
public class ConcernTypeConverter implements Converter<ConcernType,String> {
    @Override
    public String convert(ConcernType concernType) {
        return concernType.toString();
    }
}
