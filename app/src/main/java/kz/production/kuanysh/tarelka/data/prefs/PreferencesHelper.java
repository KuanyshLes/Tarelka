/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package kz.production.kuanysh.tarelka.data.prefs;


import java.util.List;

import kz.production.kuanysh.tarelka.data.DataManager;

/**
 * Created by janisharali on 27/01/17.
 */

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    Long getCurrentUserId();

    void setCurrentUserId(Integer userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentStatus();

    void setCurrentStatus(String status);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getPhoneNumber();

    void setPhoneNumber(String phone);

    String getImage();

    void setImage(String image);

    String getAge();

    void setAge(String age);

    String getWeight();

    void setWeight(String weight);

    String getAims();

    void setAims(String aims);

    String getHeight();

    void setHeight(String height);


}
