package Controller;

import android.content.Context;

import java.util.ArrayList;

import Bean.UserBean;
import Dao.LocalSqltools;

/**
 * Created by 14063 on 2017/11/28.
 */

public class UsersController {
    LocalSqltools mSql;
    public UsersController(Context context) {
      mSql=new LocalSqltools(context);
    }
   /*
   * 用户名是否为唯一
   * return true ,为唯一并保存
   * */
   public boolean  userOnlyName(UserBean usersBean){
       if (mSql.getOnlyOneUsers(usersBean.userName)>0){
           return false;
       }
       mSql.saveUsers(usersBean);
       return true;
   }
    public String  getUsersInfo(UserBean usersBean){
        return mSql.getUsersInfoAndNameByKey(usersBean);
    }
}
