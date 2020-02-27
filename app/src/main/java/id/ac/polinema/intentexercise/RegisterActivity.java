package id .ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRMPASS_KEY = "confirmpass";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUTYOU_KEY = "aboutyou";

    @NotEmpty
    private EditText Fullname;
    @NotEmpty
    @Email
    private EditText Email;
    @NotEmpty
    @Password
    private EditText Password;
    @NotEmpty
    @ConfirmPassword
    private EditText ConfirmPassword;
    @NotEmpty
    private EditText Homepage;
    @NotEmpty
    private EditText AboutYou;
    protected Validator validator;
    private ImageView ImageProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Fullname = findViewById(R.id.text_fullname);
        Email = findViewById(R.id.text_email);
        Password = findViewById(R.id.text_password);
        ConfirmPassword = findViewById(R.id.text_confirm_password);
        Homepage = findViewById(R.id.text_homepage);
        AboutYou = findViewById(R.id.text_about);
        ImageProfil = findViewById(R.id.image_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try{
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ImageProfil.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }


    @Override
    public void onValidationSucceeded() {

        String name = Fullname.getText().toString();
        String email = Email.getText().toString();
        String pass = Password.getText().toString();
        String confpass = ConfirmPassword.getText().toString();
        String home = Homepage.getText().toString();
        String about = AboutYou.getText().toString();

        ImageProfil.setDrawingCacheEnabled(true);
        Bitmap b=ImageProfil.getDrawingCache();


        Intent intent = new Intent(this, ProfileActivity.class);

        intent.putExtra(FULLNAME_KEY, name);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, pass);
        intent.putExtra(CONFIRMPASS_KEY, confpass);
        intent.putExtra(HOMEPAGE_KEY, home);
        intent.putExtra(ABOUTYOU_KEY, about);
        intent.putExtra("Bitmap", b);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void handlePict(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void okHandle(View view) {
        validator.validate();
    }

}