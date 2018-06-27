package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import Bean.StorageApplication;
import Bean.UserBean;
import LocalSql.CreatSql;

/**
 * Created by 14063 on 2017/11/27.
 */

public class LocalSqltools {
    CreatSql sqlHelper;
    SQLiteDatabase sql;
    Context context;
    int num;//记录条数

    public LocalSqltools(Context context) {
        this.context = context;
        this.sqlHelper = new CreatSql(context);
        sql = sqlHelper.getReadableDatabase();
    }

    /*
  *
  *   public   int id;
  public  String userName;
  public  String userPassWord;
  public  int sex;
  public  String email;
  public  String userPhone;
  public  String userAddress;
  public  int userStatus;
  public  int userCode;
  public  int informationCode;
  public String userCreatTime;
  */
    public void saveUsers(ArrayList<UserBean> list) {

        for (UserBean usersBean : list) {
            ContentValues value = new ContentValues();
            value.put("userName", usersBean.userName);
            value.put("userPassWord", usersBean.userPassWord);
            value.put("sex", usersBean.sex);
            value.put("email", usersBean.email);
            value.put("userPhone", usersBean.userPhone);
            value.put("userAddress", usersBean.userAddress);
            value.put("userStatus", usersBean.userStatus);
            value.put("userCode", usersBean.userCode);
            value.put("informationCode", usersBean.informationCode);
            value.put("userCreatTime", usersBean.userCreatTime);
            sql.insert("user", null, value);
        }

    }

    public void saveUsers(UserBean usersBean) {
        ContentValues value = new ContentValues();
        value.put("userName", usersBean.userName);
        value.put("userPassWord", usersBean.userPassWord);
        value.put("sex", usersBean.sex);
        value.put("email", usersBean.email);
        value.put("userPhone", usersBean.userPhone);
        value.put("userAddress", usersBean.userAddress);
        value.put("userStatus", usersBean.userStatus);
        value.put("userCode", usersBean.userCode);
        value.put("informationCode", usersBean.informationCode);
        value.put("userCreatTime", usersBean.userCreatTime);
        sql.insert("user", null, value);

    }

    public ArrayList<HashMap<String, String>> getAllUsers(String name) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery("select * from user", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<>();
                if (!name.equals(cursor.getString(1))) {
                    map.put("id", cursor.getInt(0) + "");
                    map.put("userName", cursor.getString(1));
                    map.put("userPassWord", cursor.getString(2));
                    map.put("sex", cursor.getInt(3) + "");
                    map.put("email", cursor.getString(4));
                    map.put("userPhone", cursor.getString(5));
                    map.put("userAddress", cursor.getString(6));
                    map.put("userStatus", cursor.getInt(7) + "");
                    map.put("userCode", cursor.getInt(8) + "");
                    map.put("informationCode", cursor.getInt(9) + "");
                    map.put("userCreatTime", cursor.getString(10));
                    map.put("Flags", 0+"");
                    list.add(map);
                }


            }
        }
        cursor.close();
        return list;

    }

    public int getOnlyOneUsers(String name) {
        Cursor cursor = sql.rawQuery("select * from user where userName=?", new String[]{name});
        num = cursor.getCount();
        return num;
    }

    public String getUsersInfoAndNameByKey(UserBean bean) {

        Cursor cursor = sql.rawQuery("select * from user where userName=?", new String[]{bean.userName});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (!cursor.getString(2).equals(bean.userPassWord)) {
                    return "密码错误!";
                }
                bean.id = cursor.getInt(0);
                bean.userName = cursor.getString(1);
                bean.userPassWord = cursor.getString(2);
                bean.sex = cursor.getInt(3);
                bean.email = cursor.getString(4);
                bean.userPhone = cursor.getString(5);
                bean.userAddress = cursor.getString(6);
                bean.userStatus = cursor.getInt(7);
                bean.userCode = cursor.getInt(8);
                bean.informationCode = cursor.getInt(9);
                bean.userCreatTime = cursor.getString(10);
            }
        } else {
            return "该用户不存在!";
        }
        return "登陆成功!";
    }
}
