package taichu.mobi.android.framework.vcad.demo.unit.login.action;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import taichu.mobi.android.framework.vcad.demo.unit.login.data.LoginInfo;

import cz.msebera.android.httpclient.Header;


public class LoginAction implements ILoginAction {
    //private ILoginController controller;
    private ILoginHandler loginHandler;
    public LoginAction(ILoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @Override
    public void login(LoginInfo info) {
        RequestParams request = new RequestParams();
        request.put("username",info.getUserName());
        request.put("password",info.getPassword());
        new AsyncHttpClient().post(null, "http://192.168.12.12/app/user/login", request, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                loginHandler.onFailure( statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                loginHandler.onSuccess(statusCode, headers, responseString);
            }
        });
    }

    public interface ILoginHandler{
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable);
        public void onSuccess(int statusCode, Header[] headers, String responseString);
    }
}
