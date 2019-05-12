package c.gla.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsActivity extends AppCompatActivity {
    private ImageView selectNewsImage;
    private Button updateNewsButton;
    private EditText newsTitle, newsDescription;
    private Uri imageUri;
    private String Title, Description;
    private News news;

    private DatabaseReference firebaseDatabaseuri;
    private StorageReference NewsImageReference;

    private String saveCurrentDate,saveCurrentTime, newsRandomName,downloadUrl;

    private static final int Gallery_Pick = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setTitle("News Feed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseDatabaseuri= FirebaseDatabase.getInstance().getReference("News");
        selectNewsImage = findViewById(R.id.select_news_image);
        updateNewsButton = findViewById(R.id.update_news_button);
        newsTitle = findViewById(R.id.news_title);
        newsDescription = findViewById(R.id.news_description);

        NewsImageReference = FirebaseStorage.getInstance().getReference();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add News");

        selectNewsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallary();
            }
        });

        updateNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateNewsInfo();
            }
        });

    }

    private void ValidateNewsInfo() {
        Description = newsDescription.getText().toString();
        Title = newsTitle.getText().toString();
        if(imageUri == null){
            Toast.makeText(this, "select Post Image???", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Write Something for News....", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Title)){
            Toast.makeText(this, "Add News Title", Toast.LENGTH_SHORT).show();

        }
        else {
            StoringImageToFirebaseStorage();
        }


    }

    private void StoringImageToFirebaseStorage() {

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        newsRandomName = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = NewsImageReference.child("News Images").
                child(imageUri.getLastPathSegment() + newsRandomName + ".jpg");

        filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        news = new News(newsRandomName, Title, Description, uri.toString());
                        Toast.makeText(NewsActivity.this, ""+uri.toString(), Toast.LENGTH_SHORT).show();
                        firebaseDatabaseuri.child(newsRandomName).setValue(news).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(NewsActivity.this, "success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewsActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                        });
                    }
                });
            }
        });


    }

    private void OpenGallary() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Pick && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            selectNewsImage.setImageURI(imageUri);
        }
    }
}
