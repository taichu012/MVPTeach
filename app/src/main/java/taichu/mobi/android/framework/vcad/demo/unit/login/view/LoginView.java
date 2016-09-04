package taichu.mobi.android.framework.vcad.demo.unit.login.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.mvpteach.liuguangli.mvpteach.R;

import taichu.mobi.android.framework.vcad.demo.unit.login.controller.LoginController;
import taichu.mobi.android.framework.vcad.demo.unit.login.controller.ILoginController;

/**
 * 登录功能MVP设计
 */
public class LoginView extends AppCompatActivity  implements ILoginView {

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ILoginController mLoginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginController = new LoginController(this);
    }

    /**
     * 提交登录
     */
    @Override
    public void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }
        if (!cancel){
            mLoginController.loginToServer(email,password);
			//taichu: presenter层表达的是能力集合，简单认为就是func list，而既然是面向能力的，就应该清晰的表达
			//登录到服务器的输入是<账号email,密码>，而不是放入request这个信息的容器（http交互产生的容器封装）；
			//mvp本例中的loginToServer传入账号密码恰到好处，不多不少，正好；
			//mvpbad坏例子中传入request只是容器，并非表达loginToServer的真正func含义和最小数据出入集合！
        }


    }

    /**
     * 显示错误信息
     * @param code        错误码
     * @param devMsg      技术性提示信息
     * @param friendlyMsg 用户提示信息
     */
    @Override
    public void ShowErrorMsg(int code, String devMsg, String friendlyMsg) { /*待实现*/ }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccessful() {/*待实现*/}

    /**
     *  显示进度
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showLoginProcessingState(final boolean processingOrProcessed) {
        mProgressView.setVisibility(processingOrProcessed ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(processingOrProcessed ? View.GONE : View.VISIBLE);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

