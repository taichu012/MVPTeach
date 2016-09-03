package taichu.mobi.android.framework.vcad.demo.unit.login.controller;

import taichu.mobi.android.framework.vcad.demo.unit.login.data.UserInfo;

public interface ILoginController {
    /**
     * 登录
     * @param userName
     * @param password
     */
    public void loginToServer(String userName, String password);
    /**
     * 登录成功
     */
    public void loginSuc(UserInfo userInfo);
    /**
     * 登录失败
     */
    public void loginError(int code, String msg);

}
