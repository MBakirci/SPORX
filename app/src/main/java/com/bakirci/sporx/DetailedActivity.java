package com.bakirci.sporx;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bakirci.sporx.Controllers.AppController;
import com.bakirci.sporx.Models.NewsDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {

    public static String URL = "http://www.sporx.com/_json/_detay/haber_detay.php?id=";
    private NewsDetails news;
    private TextView title;
    private TextView headline;
    private TextView body;
    private TextView date;
    private TextView source;
    private ImageView image;
    private TextView cats;
    private ImageLoader mImageLoader;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImageLoader = AppController.getInstance().getImageLoader();


        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FetchFrontPage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void FetchFrontPage() {
        news = null;
        int id = (Integer) getIntent().getExtras().get("News");
        String url = String.format(URL + "%d", id);

        //Start!
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject newsObject = (JSONObject) response
                                        .get(i);
                                setDetails(newsObject);
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                        DetailedViewholder();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(req, "NewsDetails");
    }

    private void setDetails(JSONObject response) throws JSONException, ParseException {
        this.news = new NewsDetails(response.getInt("id"));

        this.news
                .setCategory(getCats(response.getJSONArray("cats")));

        this.news
                .setTitle(response.getString("title"));

        this.news
                .setHeadline(response.getString("headline"));

        this.news
                .setImageUrl(response.getString("image_big"));

        this.news
                .setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                        .parse(response.getString("date")));
        this.news
                .setSource(response.getString("source"));

        this.news
                .setBody(Html.fromHtml(response.getString("body")
                        .replaceAll("[\\t\\n\\r]", "")
                        .replaceAll("\\<style[^>]*>[\\s\\S]*?</style>", "")
                        .replaceAll("\\<a[^>]*>[\\s\\S]*?</a>", "")).toString());
    }

    private void DetailedViewholder() {
        title = (TextView) findViewById(R.id.titleDetailed);
        headline = (TextView) findViewById(R.id.headlineDetailed);
        headline.setTypeface(null, Typeface.BOLD);
        body = (TextView) findViewById(R.id.bodyDetailed);
        date = (TextView) findViewById(R.id.dateDetailed);
        source = (TextView) findViewById(R.id.sourceDetailed);
        image = (ImageView) findViewById(R.id.imageDetailed);
        cats = (TextView) findViewById(R.id.catsDetailed);

        fillViewHolder(news);
    }

    private void fillViewHolder(NewsDetails news) {

        title.setText(news.getTitle());
        headline.setText(news.getHeadline());
        body.setText(news.getBody());
        date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(news.getDate()));
        source.setText(String.format("Kaynak: %s", news.getSource()));
        mImageLoader.get(news.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        cats.setText(Arrays.toString(news.getCategory()));

        getSupportActionBar().setTitle(news.getCategory()[0]);

    }

    private String[] getCats(JSONArray catsArray) throws JSONException {
        String[] cats = new String[catsArray.length()];
        for (int i = 0; i < catsArray.length(); i++) {
            cats[i] = catsArray.getJSONObject(i).getString("name");
        }
        return cats;
    }

}
