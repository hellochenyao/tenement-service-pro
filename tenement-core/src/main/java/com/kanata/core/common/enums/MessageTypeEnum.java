package com.kanata.core.common.enums;

public enum MessageTypeEnum implements EnumsValue {
    WENZI("text",0),
    TUPIAN("img",-1),
    YUYIN("voice",1);

    private int code;
    private String value;

    MessageTypeEnum(String value,int code){
        this.code = code;
        this.value = value;
    }
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public  String getValue() {
        return value;
    }
    public static MessageTypeEnum getEnumByCode(int code){
        MessageTypeEnum[] messageTypeEnums= MessageTypeEnum.values();
        for(MessageTypeEnum messageTypeEnum : messageTypeEnums){
            if(messageTypeEnum.getCode()==code){
                return messageTypeEnum;
            }
        }
        return null;
    }
    public static MessageTypeEnum getEnumByValue(String value){
        MessageTypeEnum[] messageTypeEnums= MessageTypeEnum.values();
        for(MessageTypeEnum messageTypeEnum : messageTypeEnums){
            if(messageTypeEnum.getValue().equals(value)){
                return messageTypeEnum;
            }
        }
        return null;
    }
}
