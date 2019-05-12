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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class PostActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private ImageView selectPostImage;
    private Button updatePostButton;
    private EditText postDescription,postTitle;
    private Uri ImageUri;
    private String Description,Title;
    private Post post;

    private DatabaseReference firebaseDatabaseuri;
    private StorageReference PostImagesReference;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl;

    private static final int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        getSupportActionBar().setTitle("Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        firebaseDatabaseuri = FirebaseDatabase.getInstance().getReference("POST");


        progressBar = findViewById(R.id.progress_Bar);
        progressBar.setVisibility(View.INVISIBLE);

        selectPostImage = findViewById(R.id.select_post_image);
        updatePostButton = findViewById(R.id.update_post_button);
        postDescription = findViewById(R.id.post_description);
        postTitle = findViewById(R.id.post_title);

        PostImagesReference = FirebaseStorage.getInstance().getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Post");


        selectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        updatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });

    }

    private void ValidatePostInfo() {
        Description = postDescription.getText().toString();
        Title = postTitle.getText().toString();
        if (ImageUri == null) {
            Toast.makeText(this, "select Post Image???", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description) && TextUtils.isEmpty(Title)) {
            Toast.makeText(this, "Write something for post", Toast.LENGTH_SHORT).show();
        } else {
            StoringImageToFirebaseStorage();
        }
    }

    private void StoringImageToFirebaseStorage() {

        progressBar.setVisibility(View.VISIBLE);
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = PostImagesReference.child("Post Picture").
                child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        post = new Post(postRandomName,postDescription.getText().toString(),uri.toString(),
                                postTitle.getText().toString());

                        Toast.makeText(PostActivity.this, "" + uri.toString(), Toast.LENGTH_SHORT).show();

                        firebaseDatabaseuri.child(postRandomName).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                              progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(PostActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

               /* if(task.isSuccessful()){
                    UploadTask.TaskSnapshot downUri = task.getResult();
                    downloadUrl = downUri.toString();
                    Toast.makeText(PostActivity.this, "d" + downloadUrl, Toast.LENGTH_LONG).show();
                    Toast.makeText(PostActivity.this, "Image Uploaded successfully...", Toast.LENGTH_SHORT).show();
                }
                else{

                    String message = task.getException().getMessage();
                    Toast.makeText(PostActivity.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                }
*/
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            selectPostImage.setImageURI(ImageUri);
        }
    }
}
