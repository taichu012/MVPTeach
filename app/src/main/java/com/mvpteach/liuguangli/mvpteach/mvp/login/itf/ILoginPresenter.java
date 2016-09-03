package com.mvpteach.liuguangli.mvpteach.mvp.login.itf;

import com.mvpteach.liuguangli.mvpteach.mvp.login.bean.UserInfo;

/**
 * Created by liuguangli on 16/1/18.
 */
public interface ILoginPresenter {
    /**
     * 登录
     * @param userName
     * @param password
     */
    public void loginToServer(String userName,String password);
    /**
     * 登录成功
     */
    public void loginSuc(UserInfo userInfo);
    /**
     * 登录失败
     */
    public void loginError(int code, String msg);

}
