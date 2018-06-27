package kz.production.kuanysh.tarelka.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 20.06.2018.
 */

public class Profile implements Parcelable{
    private String name;
    private String weight;
    private String age;
    private String aim;
    private int photo;
    private String phone;

    public Profile() {
    }

    public Profile(String name, String weight, String age, String aim, String phone) {
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.aim = aim;
        this.phone = phone;
    }

    protected Profile(Parcel in) {
        name = in.readString();
        age= in.readString();
        weight= in.readString();
        aim= in.readString();
        phone= in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getAge() {
        return age;
    }

    public String getAim() {
        return aim;
    }

    public int getPhoto() {
        return photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(weight);
        dest.writeString(aim);
        dest.writeString(phone);
    }
}
