package LocalSql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 14063 on 2017/11/27.
 */

public class CreatSql extends SQLiteOpenHelper {
    public CreatSql(Context context) {
        super(context, "UserInfo", null, 1);
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
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id primary key," +
                "userName varchar(20)," +
                "userPassWord varchar(36)," +
                "sex integer," +
                "email varchar(20)," +
                "userPhone varchar(20)," +
                "userAddress varchar(100)," +
                "userStatus integer," +
                "userCode integer," +
                "informationCode integer," +
                " userCreatTime varchar(100));");
        db.execSQL("create table message(_id primary key," +
                "userName varchar(20)," +
                "message varchar(100)," +
                "messageStatus integer," +
                "userCode integer," +
                "informationCode integer," +
                " userCreatTime varchar(100));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
