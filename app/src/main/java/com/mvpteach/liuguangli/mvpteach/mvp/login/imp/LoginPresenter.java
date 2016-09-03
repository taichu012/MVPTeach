package com.mvpteach.liuguangli.mvpteach.mvp.login.imp;

import android.os.Handler;
import android.os.Message;

import com.mvpteach.liuguangli.mvpteach.mvp.login.bean.LoginInfo;
import com.mvpteach.liuguangli.mvpteach.mvp.login.bean.UserInfo;
import com.mvpteach.liuguangli.mvpteach.mvp.login.itf.ILoginManager;
import com.mvpteach.liuguangli.mvpteach.mvp.login.itf.ILoginPresenter;
import com.mvpteach.liuguangli.mvpteach.mvp.login.itf.ILoginView;

/**
 * mvp分层
 */
public class LoginPresenter extends Handler implements ILoginPresenter {
    public final static int  METHOD_LONIN_ERROR = 0;
    public final static int  METHOD_LONIN_SUC = 2;
    private ILoginManager loginManager;
    private ILoginView loginView;


    public LoginPresenter(ILoginView view){
        loginManager = new LoginManager(this);
        this.loginView = view;
    }
    @Override
    public void loginToServer(String userName,String password) {
        LoginInfo info = new LoginInfo();
        info.setUserName(userName);
        info.setPassword(password);
        loginView.showProcess(true);
        loginManager.login(info);
    }

    @Override
    public void loginSuc(UserInfo userInfo) {
        // TODO: 16/1/23 save userInfo
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
