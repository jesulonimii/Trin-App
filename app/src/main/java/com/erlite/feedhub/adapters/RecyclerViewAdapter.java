package com.erlite.feedhub.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.erlite.feedhub.ReadActivity;
import com.erlite.feedhub.models.Posts;
import com.erlite.feedhub.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Posts> mList;

    public RecyclerViewAdapter(Context mContext, List<Posts> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.blog_post_item, parent, false);

        final MyViewHolder viewHolder = new MyViewHolder(view) ;




        //click to lead to post activity
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, ReadActivity.class);

                //add all views to be animated in transition
                Pair imageTransition = Pair.create(viewHolder.postImage, ViewCompat.getTransitionName(viewHolder.postImage));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, imageTransition);

                intent.putExtra("post_title", mList.get(viewHolder.getAdapterPosition()).getTitle());
                intent.putExtra("post_description", mList.get(viewHolder.getAdapterPosition()).getDescription());
                intent.putExtra("post_date", mList.get(viewHolder.getAdapterPosition()).getDate());
                intent.putExtra("post_category", mList.get(viewHolder.getAdapterPosition()).getCategory());
                intent.putExtra("post_image", mList.get(viewHolder.getAdapterPosition()).getImg_url());
                intent.putExtra("post_content", mList.get(viewHolder.getAdapterPosition()).getContent());
                intent.putExtra("post_id", mList.get(viewHolder.getAdapterPosition()).getId());


                mContext.startActivity(intent, options.toBundle());

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.postTitle.setText(mList.get(position).getTitle());
        holder.postDescription.setText(mList.get(position).getDescription());
        holder.postDate.setText(mList.get(position).getDate());
        holder.postCategory.setText(mList.get(position).getCategory());


        //glide
        Glide.with(mContext).load(mList.get(position).getImg_url()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView postTitle;
        TextView postDescription;
        TextView postCategory;
        TextView postDate;
        ImageView postImage;
        CardView view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            view_container = itemView.findViewById(R.id.container);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
            postCategory = itemView.findViewById(R.id.postCategory);
            postDate = itemView.findViewById(R.id.postDate);
            postImage = itemView.findViewById(R.id.postImage);

        }

    }

}
