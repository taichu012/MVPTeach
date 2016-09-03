package taichu.mobi.android.framework.vcad.demo.unit.login.controller;

import android.os.Handler;
import android.os.Message;

import taichu.mobi.android.framework.vcad.demo.unit.login.data.LoginInfo;
import taichu.mobi.android.framework.vcad.demo.unit.login.data.UserInfo;
import taichu.mobi.android.framework.vcad.demo.unit.login.action.LoginAction;
import taichu.mobi.android.framework.vcad.demo.unit.login.action.ILoginAction;
import taichu.mobi.android.framework.vcad.demo.unit.login.view.ILoginView;

/**
 * mvp分层
 */
public class LoginController extends Handler implements ILoginController {
    public final static int  METHOD_LONIN_ERROR = 0;
    public final static int  METHOD_LONIN_SUC = 2;
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
        loginView.showProcess(true);
        loginAction.login(info);
    }

    @Override
    public void loginSuc(UserInfo userInfo) {
        Message msg = obtainMessage();
        msg.what = METHOD_LONIN_SUC;
        sendMessage(msg);
    }

    @Override
    public void loginError(int code, String msgInfo) {
        Message msg = obtainMessage();
        msg.what = METHOD_LONIN_SUC;
        msg.arg1 = code;
        msg.obj = msgInfo;
        sendMessage(msg);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case METHOD_LONIN_ERROR:
                loginErroOnUi(msg.arg1,msg.obj.toString());
                break;
            case METHOD_LONIN_SUC:
                loginSucOnUi();
                break;
        }
    }

    public void loginErroOnUi(int code,String message){
        loginView.showErrorInfo(code, message, message);
        loginView.showProcess(false);
    }

    private void loginSucOnUi(){
        loginView.showProcess(false);
        loginView.loginSuc();
    }
}
