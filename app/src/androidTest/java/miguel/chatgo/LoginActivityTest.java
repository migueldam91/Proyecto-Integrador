package miguel.chatgo;

import android.app.Dialog;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;


import com.parse.ParseUser;



/**
 * Created by Miguel on 2/2/2016.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private EditText usernameField, passwordField;
    private Button btnlogin;
    private LoginActivity loginActivity;
    private Dialog alertDialog;

    public LoginActivityTest(Class<LoginActivity> activityClass) {
        super(activityClass);
    }


    public LoginActivityTest(){
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        loginActivity=getActivity();
        usernameField = (EditText) loginActivity.findViewById(R.id.usernameField);
        passwordField = (EditText) loginActivity.findViewById(R.id.passwordField);
        btnlogin = (Button) loginActivity.findViewById(R.id.actionButton);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
    private static final String LOGIN = "foo";
    private static final String PASSWORD = "bar";

    public void testLogearse() throws Exception {
        if(ParseUser.getCurrentUser()!=null)
            ParseUser.logOut();

        TouchUtils.tapView(this, usernameField);
        getInstrumentation().sendStringSync(LOGIN);
        TouchUtils.tapView(this, passwordField);
        getInstrumentation().sendStringSync(PASSWORD);
        //Testea los parámetros
        TouchUtils.clickView(this, btnlogin);

        assertNotNull("Test Login", ParseUser.logIn(LOGIN, PASSWORD));
        //assertTrue("Test Login", loginActivity.logearse(btnlogin));
    }


}