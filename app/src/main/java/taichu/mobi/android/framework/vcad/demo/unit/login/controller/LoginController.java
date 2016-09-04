package taichu.mobi.android.framework.vcad.demo.unit.login.controller;

import android.os.Handler;
import android.os.Message;

import cz.msebera.android.httpclient.Header;
import taichu.mobi.android.framework.vcad.demo.unit.login.data.LoginInfo;
import taichu.mobi.android.framework.vcad.demo.unit.login.action.LoginAction;
import taichu.mobi.android.framework.vcad.demo.unit.login.action.ILoginAction;
import taichu.mobi.android.framework.vcad.demo.unit.login.view.ILoginView;

/**
 * mvp分层
 */
public class LoginController extends Handler implements ILoginController, LoginAction.ILoginHandler {
    public final static int METHOD_LOGIN_FAILED = 0;
    public final static int METHOD_LOGIN_SUCCESSFUL = 2;
    private ILoginAction loginAction;
    private ILoginView loginView;


    public LoginController(ILoginView view){
        loginAction = new LoginAction(this);
        this.loginView = view;
    }
    @Override
    public void loginToServer(String userName,String password) {
        LoginInfo info = new LoginInfo();
        info.setUserName(userName);
        info.setPassword(password);
        loginView.showLoginProcessingState(true);
        loginAction.login(info);
    }

    @Override
    public void loginSuccessful(int code, String msgInfo) {
        Message msg = obtainMessage();
        msg.what = METHOD_LOGIN_SUCCESSFUL;
        msg.arg1 = code;
        msg.obj = msgInfo;
        sendMessage(msg);
    }

    @Override
    public void loginFailed(int code, String msgInfo) {
        Message msg = obtainMessage();
        msg.what = METHOD_LOGIN_FAILED;
        msg.arg1 = code;
        msg.obj = msgInfo;
        sendMessage(msg);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case METHOD_LOGIN_FAILED:
                loginFailedShownInView(msg.arg1,msg.obj.toString());
                break;
            case METHOD_LOGIN_SUCCESSFUL:
                loginSuccessfulShownInView(msg.arg1,msg.obj.toString());
                break;
        }
    }

    public void loginFailedShownInView(int code, String message){
        loginView.ShowErrorMsg(code, message, message);
        loginView.showLoginProcessingState(false);
    }

    private void loginSuccessfulShownInView(int code, String message){
        loginView.showLoginProcessingState(false);
        loginView.loginSuccessful();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        loginFailed(statusCode, responseString);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        loginSuccessful(statusCode, responseString);
    }
}
