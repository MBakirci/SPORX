package com.bakirci.sporx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bakirci.sporx.Adapters.FrontPageAdapter;
import com.bakirci.sporx.Controllers.AppController;
import com.bakirci.sporx.Models.NewsFrontPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment
        extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String URL = "http://www.sporx.com/_json/_haber/manset.php";

    private NewsFrontPage news;
    private List<NewsFrontPage> newses;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FrontPageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        newses = new LinkedList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(llm);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                FetchFrontPage();
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        recyclerView.setAdapter(null);
        FetchFrontPage();
    }

    void FetchFrontPage() {
        //Setup adapter
        adapter = new FrontPageAdapter(newses);
        recyclerView.setAdapter(adapter);

        //Ready for loading
        swipeRefreshLayout.setRefreshing(true);
        newses.clear();

        //Start!
        JsonArrayRequest req = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject newsObject = (JSONObject) response
                                        .get(i);
                                if (!newsObject.getString("galeriId").isEmpty()
                                        && Integer.parseInt(newsObject.getString("galeriId")) == 0
                                        && !newsObject.getString("source").isEmpty()) {
                                    NewsFrontPage news = setDetail(newsObject);
                                    newses.add(news);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Collections.sort(newses, new Comparator<NewsFrontPage>() {
                            @Override
                            public int compare(NewsFrontPage n1, NewsFrontPage n2) {
                                return (new Integer(n2.getComment_size()).compareTo(new Integer(n1.getComment_size())));
                            }
                        });
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    private NewsFrontPage setDetail(JSONObject response) throws JSONException {
        NewsFrontPage news = new NewsFrontPage(response.getInt("id"));
        news.setTitle(response.getString("title"));
        news.setHeadline(response.getString("headline"));
        news.setImageUrl(response.getString("image"));
        news.setComment_size(Integer.parseInt(response.getString("comment_count")));
        return news;
    }

}
