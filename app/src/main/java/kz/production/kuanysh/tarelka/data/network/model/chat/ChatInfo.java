package kz.production.kuanysh.tarelka.data.network.model.chat; ;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatInfo implements Parcelable
{

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<ChatInfo> CREATOR = new Creator<ChatInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatInfo createFromParcel(Parcel in) {
            return new ChatInfo(in);
        }

        public ChatInfo[] newArray(int size) {
            return (new ChatInfo[size]);
        }

    }
            ;

    protected ChatInfo(Parcel in) {
        this.result = ((Result) in.readValue((Result.class.getClassLoader())));
        this.statusCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ChatInfo() {
    }

    /**
     *
     * @param message
     * @param statusCode
     * @param result
     */
    public ChatInfo(Result result, Integer statusCode, String message) {
        super();
        this.result = result;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(result);
        dest.writeValue(statusCode);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}