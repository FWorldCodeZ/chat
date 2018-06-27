package Bean;

import android.app.Application;

/**
 * Created by 14063 on 2017/12/2.
 */

public class StorageApplication extends Application {

    private   int id;
    private  String userName;
    private  String userPassWord;
    private  String sex;
    private  String email;
    private  String userPhone;
    private  String userAddress;
    private  int userStatus;
    private  int userCode;
    private  int informationCode;
    private String userCreatTime;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getInformationCode() {
        return informationCode;
    }

    public void setInformationCode(int informationCode) {
        this.informationCode = informationCode;
    }

    public String getUserCreatTime() {
        return userCreatTime;
    }

    public void setUserCreatTime(String userCreatTime) {
        this.userCreatTime = userCreatTime;
    }
}
