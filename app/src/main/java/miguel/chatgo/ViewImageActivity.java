package miguel.chatgo;

import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {
    private ImageView photoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        photoFile= (ImageView) findViewById(R.id.photoFile);
        Uri photoFileUri= getIntent().getData();
        /*photoFile.setImageURI(
                Picasso.with(this).load(
                        photoFileUri.toString())
                .into(photoFile)
        );*/
        Picasso.with(this).load(
                photoFileUri.toString())
                .into(photoFile);
    }
}
