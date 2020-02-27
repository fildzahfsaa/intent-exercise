package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;

public class ProfileActivity extends AppCompatActivity {

    private TextView FullnameText;
    private TextView EmailText;
    private TextView HomepageText;
    private TextView AboutText;
    private ImageView ImageProfil;
    String homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FullnameText = findViewById(R.id.label_fullname);
        EmailText = findViewById(R.id.label_email);
        HomepageText = findViewById(R.id.label_homepage);
        AboutText = findViewById(R.id.label_about);
        ImageProfil = findViewById(R.id.image_profile);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            String fullname = extras.getString(RegisterActivity.FULLNAME_KEY);
            FullnameText.setText(fullname);
            String email = extras.getString(RegisterActivity.EMAIL_KEY);
            EmailText.setText(email);
            homepage = extras.getString(RegisterActivity.HOMEPAGE_KEY);
            HomepageText.setText(homepage);
            String about = extras.getString(RegisterActivity.ABOUTYOU_KEY);
            AboutText.setText(about);

            Bitmap bitmap = (Bitmap) extras.get("Bitmap");
            ImageProfil.setImageBitmap(bitmap);

        }
    }


    public void homepageHandler(View view) {
        Uri webpage = Uri.parse(homepage);
        if (!homepage.startsWith("https://") && !homepage.startsWith("http://")){
            homepage = "http://" + homepage;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
