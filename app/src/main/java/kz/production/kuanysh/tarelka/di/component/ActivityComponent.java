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

package kz.production.kuanysh.tarelka.di.component;


import dagger.Component;
import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.di.module.ActivityModule;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.activities.test.TestActivity;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.fragments.ChatFragment;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressFragment;
import kz.production.kuanysh.tarelka.ui.fragments.social.SocialMediaDirectFragment;
import kz.production.kuanysh.tarelka.ui.welcome.ChooseFoodActivity;
import kz.production.kuanysh.tarelka.ui.welcome.CreateAimActivity;
import kz.production.kuanysh.tarelka.ui.welcome.LoginActivity;
import kz.production.kuanysh.tarelka.ui.welcome.SplashScreenActivity;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(MainTaskFragment mainTaskFragment);

    void inject(TaskDetailActivity taskDetailActivity);

    void inject(ProfileFragment profileFragment);


    void inject(ChatFragment chatFragment);

    void inject(ProfileEditActivity profileEditActivity);

    void inject(ProgressFragment progressFragment);

    void inject(TestActivity testActivity);

    void inject(SplashScreenActivity splashScreenActivity);

    void inject(LoginActivity loginActivity);

    void inject(CreateAimActivity createAimActivity);

    void inject(ChooseFoodActivity chooseFoodActivity);

    void inject(SocialMediaDirectFragment socialMediaDirectFragment);


}
