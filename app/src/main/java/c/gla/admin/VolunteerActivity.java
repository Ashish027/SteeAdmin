package c.gla.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class VolunteerActivity extends AppCompatActivity {

    private ImageView selectvolimage;
    private Button save;
    private EditText volname,volemail, voladdress;
    private Uri ImageUri;
    private String Name,Email, Address;
    private Volunteer volunteer;



    private DatabaseReference firebaseDatabaseuri;
    private StorageReference VolImagesReference;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl;

    private static final int Gallery_Pick = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        getSupportActionBar().setTitle("Volunteer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseDatabaseuri = FirebaseDatabase.getInstance().getReference("Volunteer");

        save = findViewById(R.id.vol_save);
        selectvolimage = findViewById(R.id.vol_upload_image);
        volname = findViewById(R.id.vol_upload_name);
        voladdress = findViewById(R.id.vol_upload_address);
        volemail = findViewById(R.id.vol_upload_email);


        VolImagesReference = FirebaseStorage.getInstance().getReference();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Volunteer");


        selectvolimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateVolInfo();
            }
        });

    }

    private void ValidateVolInfo() {

        Name = volname.getText().toString();
        Email = volemail.getText().toString();
        Address = voladdress.getText().toString();
        if(ImageUri == null){
            Toast.makeText(this, "select Volunteer Image???", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Write Name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Email)){
            Toast.makeText(this, "Write Email", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Address)){
            Toast.makeText(this, "Write Address", Toast.LENGTH_SHORT).show();
        }
        else{
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

        postRandomName = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = VolImagesReference.child("Voluteer image").
                child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        volunteer = new Volunteer(postRandomName, Name, Email, Address, uri.toString());

                        Toast.makeText(VolunteerActivity.this, "" + uri.toString(), Toast.LENGTH_SHORT).show();

                        firebaseDatabaseuri.child(postRandomName).setValue(volunteer).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(VolunteerActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && requestCode == requestCode && data!=null){
            ImageUri = data.getData();
            selectvolimage.setImageURI(ImageUri);

        }

    }
}