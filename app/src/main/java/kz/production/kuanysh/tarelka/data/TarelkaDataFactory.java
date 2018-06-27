package kz.production.kuanysh.tarelka.data;

import java.util.ArrayList;
import java.util.List;

import kz.production.kuanysh.tarelka.model.Profile;

/**
 * Created by User on 19.06.2018.
 */

public class TarelkaDataFactory {
    private static List<String> aimsList;
    private static List<String> foodsList;
    private static List<String> taskList;
    private static Profile profile;
    private static List<String> dateList;
    private static List<String> progress_task_list;


    public static Profile getProfile() {
        profile=new Profile();
        //profile=new Profile("Серик Байдылда","25","70","Цель Цель  Цель Цель Цель ","8 777 505 66 33");
        profile.setName("Серик Байдылда");
        profile.setWeight("25");
        profile.setAge("70");
        profile.setAim("Some aim");
        profile.setPhone("8 777 505 66 33");
        return profile;
    }

    public static List<String> getDateList() {
        dateList=new ArrayList<>();
        dateList.add("21 мая");
        dateList.add("22 мая");
        dateList.add("23 мая");
        dateList.add("24 мая");
        dateList.add("25 мая");
        dateList.add("26 мая");
        return dateList;
    }

    public static List<String> getProgress_task_list() {
        progress_task_list=new ArrayList<>();
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        progress_task_list.add("Stu Unger Rise And Fall A Lord of the rings seasons coming");
        return progress_task_list;
    }

    public static List<String> getAimsList(){
        aimsList=new ArrayList<>();
        aimsList.add("Бежать");
        aimsList.add("Похудеть");
        aimsList.add("Играть");
        aimsList.add("Бежать");
        aimsList.add("Сидеть");
        aimsList.add("Прыгать");
        aimsList.add("Бежать");
        aimsList.add("Читать");
        aimsList.add("Играть");
        aimsList.add("Играть");
        aimsList.add("Бежать");
        aimsList.add("Сидеть");
        aimsList.add("Играть");
        aimsList.add("Бежать");
        aimsList.add("Сидеть");

        return aimsList;
    }

    public static List<String> getFoodsList() {
        foodsList=new ArrayList<>();
        foodsList.add("Суп");
        foodsList.add("Мороженое");
        foodsList.add("Банан");
        foodsList.add("Манты");
        foodsList.add("Пельмени");
        foodsList.add("Горох");
        foodsList.add("Яблоко");
        foodsList.add("Консервы ");
        foodsList.add("Рулет");

        return foodsList;
    }

    public static List<String> getTaskList() {
        taskList=new ArrayList<>();
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        taskList.add("Stu Unger Rise And Fall Of A Lord of the rings seasons coming");
        return taskList;
    }
}
