package cloud.dishwish.ragmart.dishwish.start;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.SignupTask;

import static cloud.dishwish.ragmart.dishwish.start.StartActivity.hideKeyboard;

public class FragSignup extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private Calendar calendar;
    private EditText txtName;
    private TextView errorName;
    private EditText txtSurname;
    private TextView errorSurname;
    private EditText txtBirthDate;
    EditText txtGender;
    private EditText txtEmail;
    private TextView errorEmail;
    private EditText txtPassword;
    private TextView errorPassword;
    private EditText txtPasswordConfirm;
    private TextView errorPasswordConfirm;
    private Button btnSignup;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.start_signup_fragment,container,false);

        calendar = Calendar.getInstance();
        txtName = (EditText) view.findViewById(R.id.signup_txtName);
        errorName = (TextView) view.findViewById(R.id.signup_error_txtName);
        txtSurname = (EditText) view.findViewById(R.id.signup_txtSurname);
        errorSurname = (TextView) view.findViewById(R.id.signup_error_txtSurname);
        txtBirthDate = (EditText) view.findViewById(R.id.signup_txtBirthDate);
        txtGender = (EditText) view.findViewById(R.id.signup_txtGender);
        txtEmail = (EditText) view.findViewById(R.id.signup_txtUserEmail);
        errorEmail = (TextView) view.findViewById(R.id.signup_error_txtUserEmail);
        txtPassword = (EditText) view.findViewById(R.id.signup_txtPassword);
        errorPassword = (TextView) view.findViewById(R.id.signup_error_txtPassword);
        txtPasswordConfirm = (EditText) view.findViewById(R.id.signup_txtPasswordConfirm);
        errorPasswordConfirm = (TextView) view.findViewById(R.id.signup_error_txtPasswordConfirm);
        btnSignup = (Button) view.findViewById(R.id.signup_btnSignup);

        preferences = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editorPrefs = preferences.edit();

        //If focus changes, keyboard is hidden
        txtName.setOnFocusChangeListener(this);
        txtSurname.setOnFocusChangeListener(this);
        txtBirthDate.setOnFocusChangeListener(this);
        txtGender.setOnFocusChangeListener(this);
        txtEmail.setOnFocusChangeListener(this);
        txtPassword.setOnFocusChangeListener(this);
        txtPasswordConfirm.setOnFocusChangeListener(this);

        txtBirthDate.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        return view;
    }

    /**
     * Checks is the EditText contains a name
     * @param string EditText that refers to the user's name
     * @return true if the name contains only characters
     */
    public boolean checkCharOnly(String string) {

        string = string.toLowerCase();

        boolean verification = true;

        for(int i = 0; i<string.length();i++) {

            if(string.charAt(i)<'a' || string.charAt(i)>'z')
                verification = false;
        }

        return verification;
    }

    /**
     * Checks if the string inserted has an email format
     * @param email string containing the email
     * @return true if the string has an email format
     */
    public boolean isEmail(String email) { return Patterns.EMAIL_ADDRESS.matcher(email).matches(); }


    /**
     * Checks that the password contains at least a minimum number characters long and include a combination of
     * uppercase and lowercase letters, numbers and symbols(". , \ - + * !")
     * @param password string containing the password
     * @param min lowest number of letters the password must contain
     * @param max highest number of letters the password must contain
     * @return true if the password follows the constraint
     */
    private boolean checkPassword(String password, int min, int max) {
        return password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_.,\\-+*!]).{" + min + "," + max + "})");
    }

    public void onClickSignup(View view) {

        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String birthDate = txtBirthDate.getText().toString();
        String gender = (txtGender.getText().toString().length() > 0) ? txtGender.getText().toString().charAt(0)  + "" : "";
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String passwordConfirm = txtPasswordConfirm.getText().toString();

        boolean verification = true;

        if(email.equals("")) {
            showEmailError();
            verification = false;
        }
        if(password.equals("")) {
            showPasswordError();
            verification = false;
        }
        if(passwordConfirm.equals("")) {
            showPasswordConfirmError();
            verification = false;
        }
        if(name.equals("")) {
            showNameError();
            verification = false;
        }
        if(surname.equals("")) {
            showSurnameError();
            verification = false;
        }
        if(birthDate.equals("")) {
            showBirhDateError();
            verification = false;
        }
        if(gender.equals("")) {
            showGenderError();
            verification = false;
        }

        if(verification) {
            new SignupTask(getContext(),"REGISTER", preferences,editorPrefs).execute(name,surname,birthDate,gender,email,password);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.signup_txtBirthDate:
                onClickBirthDate(view);
                break;

            case R.id.signup_btnSignup:
                onClickSignup(view);
                break;

            case R.id.signup_txtGender:
                showRadioButtonDialog();
                break;
        }
    }

    private void showRadioButtonDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.start_gender_dialog);

        RadioGroup genderGroup = (RadioGroup) dialog.findViewById(R.id.start_gender_group);

        dialog.show();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RadioButton button = (RadioButton) group.getChildAt(i);
                    if (button.getId() == checkedId) {
                        txtGender.setText(button.getText());
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    public void onClickBirthDate(View view) {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        new DatePickerDialog(view.getContext(),date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ITALY);
        txtBirthDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String birthDate = txtBirthDate.getText().toString();
        String gender = (txtGender.getText().toString().length() > 0) ? txtGender.getText().toString().charAt(0)  + "" : "";
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String passwordConfirm = txtPasswordConfirm.getText().toString();

        boolean verification = true;

        if(!hasFocus && !txtName.isFocused() && !txtSurname.isFocused() && !txtGender.isFocused()
                && !txtEmail.isFocused() && !txtPassword.isFocused() && !txtPasswordConfirm.isFocused())
            hideKeyboard(v);

        switch (v.getId()) {

            case R.id.signup_txtUserEmail:
                if(!email.equals("") && !isEmail(email)) {
                    showEmailError();
                    verification = false;

                } else {
                    dismissEmailError();
                }
                break;

            case R.id.signup_txtPassword:
                if(!password.equals("") && !checkPassword(password,6,25)) {
                    showPasswordError();
                    verification = false;

                } else {
                    dismissPasswordError();
                }

                break;

            case R.id.signup_txtPasswordConfirm:
                if(!passwordConfirm.equals("") && !passwordConfirm.equals(password)) {
                    showPasswordConfirmError();
                    verification = false;

                } else {
                    dismissPasswordConfirmError();
                }
                break;

            case R.id.signup_txtName:
                if(!name.equals("") && !checkCharOnly(name)) {
                    showNameError();
                    verification = false;

                } else {
                    dismissNameError();
                }
                break;

            case R.id.signup_txtSurname:
                if(surname.equals("") && !checkCharOnly(surname)) {
                    showSurnameError();
                    verification = false;
                } else {
                    dismissSurnameError();
                }
                break;

            case R.id.signup_txtBirthDate:
                if(!birthDate.equals("")) { dismissBirthDateError(); }
                break;

            case R.id.signup_txtGender:
                if(!gender.equals("")) { dismissGenderError(); }
                break;
        }

        btnSignup.setClickable(verification);
    }

    /**
     * The following methods are used to display errors
     */
    private void showEmailError() {
        txtEmail.setTextColor(getResources().getColor(R.color.colorRed));
        txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_email, 0, R.drawable.ic_action_alert, 0);
        errorEmail.setText(getResources().getString(R.string.error_email));
    }
    private void showPasswordError(){
        txtPassword.setTextColor(getResources().getColor(R.color.colorRed));
        txtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_password, 0, R.drawable.ic_action_alert, 0);
        errorPassword.setText(getResources().getString(R.string.error_password_constraint));
    }
    private void showPasswordConfirmError(){
        txtPasswordConfirm.setTextColor(getResources().getColor(R.color.colorRed));
        txtPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_password, 0, R.drawable.ic_action_alert, 0);
        errorPasswordConfirm.setText(getResources().getString(R.string.error_password_confirmation));
    }
    private void showNameError(){
        txtName.setTextColor(getResources().getColor(R.color.colorRed));
        txtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_name, 0, R.drawable.ic_action_alert, 0);
        errorName.setText(getResources().getString(R.string.error_char_only));
    }
    private void showSurnameError(){
        txtSurname.setTextColor(getResources().getColor(R.color.colorRed));
        txtSurname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_name, 0, R.drawable.ic_action_alert, 0);
        errorSurname.setText(getResources().getString(R.string.error_char_only));
    }
    private void showBirhDateError(){
        txtBirthDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_date, 0, R.drawable.ic_action_alert, 0);
    }
    private void showGenderError(){
        txtGender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_gender, 0, R.drawable.ic_action_alert, 0);
    }

    /**
     * The following methods are used to reset the layout statements
     */
    private void dismissEmailError() {
        txtEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_email,0,0,0);
        txtEmail.setTextColor(getResources().getColor(R.color.colorText));
        errorEmail.setText("");
    }
    private void dismissPasswordError() {
        txtPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_password,0,0,0);
        txtPassword.setTextColor(getResources().getColor(R.color.colorText));
        errorPassword.setText("");}
    private void dismissPasswordConfirmError() {
        txtPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_password,0,0,0);
        txtPasswordConfirm.setTextColor(getResources().getColor(R.color.colorText));
        errorPasswordConfirm.setText("");}
    private void dismissNameError() {
        txtName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_name,0,0,0);
        txtName.setTextColor(getResources().getColor(R.color.colorText));
        errorName.setText("");
    }
    private void dismissSurnameError() {
        txtSurname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_name,0,0,0);
        txtSurname.setTextColor(getResources().getColor(R.color.colorText));
        errorSurname.setText("");
    }
    private void dismissBirthDateError() {
        txtBirthDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_date,0,0,0);
        txtBirthDate.setTextColor(getResources().getColor(R.color.colorText));
    }
    private void dismissGenderError() {
        txtGender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_gender,0,0,0);
        txtGender.setTextColor(getResources().getColor(R.color.colorText));
    }

}
