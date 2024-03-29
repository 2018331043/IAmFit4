package com.example.iamfit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.UUID;

public class SettingsActivity extends AppCompatActivity {

    ImageButton logoutButton;
    TextView displayName;
    DatabaseReference databaseReference;
    Button editDetailsButton;
    private Button upoladPic,aboutUs;
    private StorageReference storageReference;
    private Uri imageUri;
    private ImageView profilePic;
    private FirebaseStorage firebaseStorage;
    private String downLoadUrl=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutButton=findViewById(R.id.imageButtonLogOut);
        aboutUs=(Button)findViewById(R.id.button10);
        displayName=findViewById(R.id.textViewSettingsDisplayName);
        editDetailsButton=findViewById(R.id.button4);
        profilePic=findViewById(R.id.profile_image_recyclerview_child);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseStorage=FirebaseStorage.getInstance();
        //imageUri=new Uri();
        storageReference=FirebaseStorage.getInstance().getReference();
        upoladPic=findViewById(R.id.button3);
        upoladPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadPhoto();
            }
        });
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser =dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
                displayName.setText(currentUser.getName());
                if(!currentUser.getImageurl().toString().equals("0")){
                    Picasso.get().load(currentUser.getImageurl()).into(profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences=getSharedPreferences("LoggedInChecker", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("In","0");
                editor.commit();
                Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle();
                startActivity(i,bundle);
            }
        });
        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ChangeSettingsActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle();
                startActivity(i,bundle);
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,About_US_Activity.class);
                startActivity(intent);
            }
        });

    }
    void upLoadPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            /*ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE2);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},REQUEST_CODE3);
        //return;
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            imageUri=data.getData();
            //profilePic.setImageURI(imageUri);
            uploadPicToFirebase();
        }
    }

    private void uploadPicToFirebase() {
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        final ProgressDialog pd =new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String UID=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        //final String UID= UUID.randomUUID().toString();
        final StorageReference riversRef = storageReference.child("images/"+UID+".jpg");
        profilePic.setImageURI(imageUri);
        //Toast.makeText(SettingsActivity.this, "Upload Unsuccessful", Toast.LENGTH_LONG).show();
        //if(riversRef==null){
            //Toast.makeText(SettingsActivity.this, "Upload Unsuccessful"+riversRef, Toast.LENGTH_LONG).show();
        //}
        final UploadTask uploadTask = riversRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        //String image= UploadTask.getDownloadUrl();
                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();

                                }
                                // Continue with the task to get the download URL
                                return riversRef.getDownloadUrl();

                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    downLoadUrl = task.getResult().toString();
                                    databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageurl").setValue(downLoadUrl);
                                    //Toast.makeText(SettingsActivity.this, "Hi "+downLoadUrl, Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                        //Uri dwUri=taskSnapshot.getUploadSessionUri();
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //Snackbar.make(findViewById(R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(SettingsActivity.this, "Upload Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(SettingsActivity.this, "Upload Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent =(100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Progress: "+(int)progressPercent+"%");
                    }
                });

    }
}