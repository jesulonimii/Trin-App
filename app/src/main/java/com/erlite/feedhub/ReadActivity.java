package com.erlite.feedhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ReadActivity extends AppCompatActivity {

    TextView readTitle;
    TextView readDescription;
    TextView readId;
    TextView readContent;
    TextView readCategory;
    TextView readDate;
    ImageView readImage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        //remove white flash when entering activity
        //getWindow().setEnterTransition(null);

        //remove white flash when exiting activity -- check read activity for second part
        //getWindow().setExitTransition(null);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        readTitle = findViewById(R.id.readTitle);
        readDescription = findViewById(R.id.readDescription);
        readCategory = findViewById(R.id.readCategory);
        readContent = findViewById(R.id.readContent);
        readId = findViewById(R.id.readId);
        readDate = findViewById(R.id.readDate);
        readImage = findViewById(R.id.readImage);
        toolbar = findViewById(R.id.toolbar);

        //set actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadActivity.super.onBackPressed();
            }
        });


        //get display texts

        String title = getIntent().getExtras().getString("post_title");
        String description = getIntent().getExtras().getString("post_description");
        String date = getIntent().getExtras().getString("post_date");
        String image = getIntent().getExtras().getString("post_image");
        String category = getIntent().getExtras().getString("post_category");
        String content = getIntent().getExtras().getString("post_content");
        String id = getIntent().getExtras().getString("post_id");


        //set display to respective items

        readTitle.setText(title);
        readDescription.setText(description);
        readCategory.setText(category);
        readDate.setText(date);
        readContent.setText(content);
        readId.setText("Article Id: " + id);

        //set toolbar title
        getSupportActionBar().setTitle(title);


        //set image with glide
        Glide.with(this).load(image).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(readImage);



    }
}
