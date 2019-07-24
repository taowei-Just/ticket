package com.example.mqPush;

public enum MessageType {

    plan("plan"), issue("issue"), time("time");


    MessageType(String type) {
    }

    public static String TypeValue(MessageType type) {
        switch (type) {
            case plan:
                return "plan";
            case time:
                return "time";  
                case issue:
                return "issue";
            default:
                return "";
        }
    }

}
