package taichu.mobi.android.framework.vcad.demo.unit.login.controller;

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
    public void loginSuccessful(int code, String msg);
    /**
     * 登录失败
     */
    public void loginFailed(int code, String msg);

}
