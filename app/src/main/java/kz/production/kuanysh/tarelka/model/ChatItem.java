package kz.production.kuanysh.tarelka.model;

import kz.production.kuanysh.tarelka.utils.AppConst;

/**
 * Created by User on 21.06.2018.
 */

public class ChatItem {
    private int whoIam;
    private String message;
    private String date;

    public ChatItem(int whoIam, String message, String date) {
        this.whoIam = whoIam;
        this.message = message;
        this.date = date;
    }

    public static int getViewTypeSend() {
        return AppConst.VIEW_TYPE_SEND;
    }

    public static int getViewTypeReceive() {
        return AppConst.VIEW_TYPE_RECEIVE;
    }

    public int getWhoIam() {
        return whoIam;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
