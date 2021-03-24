package com.erlite.feedhub;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.erlite.feedhub.adapters.RecyclerViewAdapter;
import com.erlite.feedhub.fragments.AppInfoBottomSheet;
import com.erlite.feedhub.fragments.ThemeBottomSheet;
import com.erlite.feedhub.models.Posts;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BottomSheetBehavior sheetBehavior;
    private ImageView toggle;
    private List<Posts> lstPosts;
    private JsonArrayRequest request;
    private TextView articles_num;
    private RequestQueue requestQueue;
    private Toolbar toolbar;

    private Snackbar refreshSnackbar;

    private String url = "http://trin100-api.herokuapp.com/api/";

    private ImageView refreshBtn;
    private ConstraintLayout mainActivityLayout, errorImage;
    private RecyclerViewAdapter recyclerViewAdapter;
    SharedPrefs sharedPrefs;

    EditText searchArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstPosts = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewId);
        mainActivityLayout = findViewById(R.id.mainActivityLayout);
        articles_num = findViewById(R.id.article_num);
        refreshBtn = findViewById(R.id.refresh_btn);
        toolbar = findViewById(R.id.toolbar);
        toggle = findViewById(R.id.toggle);
        ConstraintLayout contentLayout = findViewById(R.id.contentLayout);
        errorImage = findViewById(R.id.errorImage);
        searchArticles = findViewById(R.id.searchArticles);


        sharedPrefs = new SharedPrefs(MainActivity.this);

        int getTheme = sharedPrefs.themeSettings;

        AppCompatDelegate.setDefaultNightMode(getTheme);



        //recycler view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(this, lstPosts);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trin 100");

        //refresh snackbar
        refreshSnackbar =  Snackbar.make(mainActivityLayout, "Refreshing Articles...", Snackbar.LENGTH_INDEFINITE);

        //main view
        sheetBehavior = BottomSheetBehavior.from(contentLayout);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        openBottomSheets();

        //menu toggle
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (sheetBehavior.getState()){

                    case BottomSheetBehavior.STATE_EXPANDED:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                }

            }
        });


        //set animation to refresh button while loading for first time
        refreshBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_indefinite));
        jsonParse();




        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshPosts();
            }
        });


        searchArticles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //remove white flash when exiting activity -- check read activity for second part
        //getWindow().setExitTransition(null);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);


        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


    }

    private void openBottomSheets() {

        //dark theme bottom sheet
        CardView themeOpencard = findViewById(R.id.darkThemeOpenCard);
        //show add card bottom sheet on click
        themeOpencard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeBottomSheet bottomSheet = new ThemeBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "TAG");
            }
        });

        //app info bottom sheet
        CardView appInfoOpencard = findViewById(R.id.appInfoOpen);
        //show add card bottom sheet on click
        appInfoOpencard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoBottomSheet bottomSheet = new AppInfoBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "TAG");
            }
        });

    }

    private void jsonParse() {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try {
                        jsonObject = response.getJSONObject(i);

                        Posts posts = new Posts();

                        posts.setTitle(jsonObject.getString("title"));
                        posts.setDescription(jsonObject.getString("description"));
                        posts.setCategory(jsonObject.getString("category"));
                        posts.setDate(jsonObject.getString("date"));
                        posts.setImg_url(jsonObject.getString("img"));
                        posts.setContent(jsonObject.getString("content"));
                        posts.setId(jsonObject.getString("id"));

                        lstPosts.add(posts);

                        String num = String.valueOf(response.length());

                        //stop refresh button animation
                        refreshBtn.clearAnimation();
                        refreshSnackbar.dismiss();

                        articles_num.setText(num);





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } // for loop

                //show error if no post
                showErrorImage();
                //set recycler view
                setupRecyclerView(lstPosts);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showErrorImage();
                articles_num.setText("0");

                final Snackbar snackbar =  Snackbar.make(mainActivityLayout, "Oops, something went wrong.", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        jsonParse();
                    }
                });
                snackbar.show();

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }

    private void setupRecyclerView(List<Posts> lstPosts) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //set items to arrange from bottom
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void showErrorImage(){

        if (lstPosts.isEmpty()){
            errorImage.setVisibility(View.VISIBLE);
        } else {
            errorImage.setVisibility(View.GONE);
        }

    }

    private void refreshPosts(){

        //clear the list filling the recyler view
        lstPosts.clear();

        //inform the adater that something changed
        recyclerViewAdapter.notifyDataSetChanged();

        //fetch the json again
        jsonParse();

        //set rotating animation
        refreshBtn.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_indefinite));

        //set dismiss action to sncakbar
        refreshSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSnackbar.dismiss();
            }
        });
        refreshSnackbar.show();

    }

}
