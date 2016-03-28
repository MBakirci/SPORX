package com.bakirci.sporx.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bakirci.sporx.Controllers.AppController;
import com.bakirci.sporx.DetailedActivity;
import com.bakirci.sporx.Models.NewsFrontPage;
import com.bakirci.sporx.R;

import java.util.List;

/**
 * Created by Mehmet on 24-3-2016.
 */
public class FrontPageAdapter extends RecyclerView.Adapter<FrontPageAdapter.FrontPageViewHolder> {

    ImageLoader imageLoader;
    private List<NewsFrontPage> newses;

    public FrontPageAdapter(List<NewsFrontPage> newses) {
        this.newses = newses;
        notifyItemRangeChanged(0, newses.size());
        imageLoader = AppController.getInstance().getImageLoader();

    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    @Override
    public void onBindViewHolder(final FrontPageViewHolder holder, final int position) {
        NewsFrontPage newsFrontPage = newses.get(position);
        holder.newsTitle.setText(newsFrontPage.getTitle());
        holder.newsDescription.setText(newsFrontPage.getHeadline());
        imageLoader.get(newsFrontPage.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.thumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        /*new ImageDownloader(holder.thumbnail,holder.thumbnail.getContext()).
                execute(newsFrontPage.getImageUrl());*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailedActivity.class);
                intent.putExtra("News", newses.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public FrontPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_firstheadline, parent, false);
        final FrontPageViewHolder frontPageViewHolder = new FrontPageViewHolder(itemView);
        return frontPageViewHolder;
    }

    public class FrontPageViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected TextView newsTitle;
        protected TextView newsDescription;
        protected ImageView thumbnail;

        public FrontPageViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            newsDescription = (TextView) itemView.findViewById(R.id.news_description);
            thumbnail = (ImageView) itemView.findViewById(R.id.news_thumbnail);
        }

    }
}
