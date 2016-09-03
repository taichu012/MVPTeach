package taichu.mobi.android.framework.vcad.demo.unit.login.action;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import taichu.mobi.android.framework.vcad.demo.unit.login.data.LoginInfo;
import taichu.mobi.android.framework.vcad.demo.unit.login.data.UserInfo;
import taichu.mobi.android.framework.vcad.demo.unit.login.controller.ILoginController;

import cz.msebera.android.httpclient.Header;


public class LoginAction implements ILoginAction {
    private ILoginController controller;

    public LoginAction(ILoginController controller) {
        this.controller = controller;
    }

    @Override
    public void login(LoginInfo info) {
        RequestParams request = new RequestParams();
        request.put("username",info.getUserName());
        request.put("password",info.getPassword());
        new AsyncHttpClient().post(null, "http://192.168.12.12/app/user/login", request, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //// TODO: 16/1/18 错误码解析和提示
                controller.loginError(statusCode, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                UserInfo userInfo = new UserInfo();
                // TODO: 16/1/18
                controller.loginSuc(userInfo);
            }
        });
    }
}
