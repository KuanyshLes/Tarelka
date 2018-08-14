package kz.production.kuanysh.tarelka.ui.fragments;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface ChatMvpView extends MvpView {

    void openSendAsSocial();

    void updateChat(List<Chat> chatList,int page_number);

    void openImagePicker();

    void openSociall();

    void openCommentDialog();






}
