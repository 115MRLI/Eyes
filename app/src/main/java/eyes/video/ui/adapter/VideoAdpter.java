package eyes.video.ui.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eyes.video.R;
import eyes.video.app.App;
import eyes.video.model.bean.Video;

public class VideoAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Video> videoList;
    private ClickListener listener;
    private boolean isonLoad = true;
    public VideoAdpter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_itme, parent, false);
        return new VideoHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoHolder) {
            VideoHolder menuHolder = (VideoHolder) holder;
            menuHolder.bindItem(videoList.get(position));
            Log.e("监听", "position: " + position + "  itemList.size()  :" + videoList.size());
            if (position == videoList.size() - 1) {
                if (isonLoad){
                    listener.onLoad();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itmecard)
        CardView itmecard;
        @BindView(R.id.correlatin_iv)
        ImageView correlatinIv;
        @BindView(R.id.correlatin_title)
        TextView correlatin_title;
        @BindView(R.id.videotype)
        TextView videotype;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(final Video item) {
            correlatin_title.setText(item.getName());
            videotype.setText(item.getType());
            Glide.with(App.mContext).load(item.getImage()).centerCrop().skipMemoryCache(true).into(correlatinIv);
            itmecard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItmeClick(item);
                }
            });
        }
    }

    public interface ClickListener {
        /**
         * Itme点击方法
         *
         * @param bean
         */
        void onItmeClick(Video bean);

        /**
         * 加载更多
         */
        void onLoad();
    }

    public void setClickListener(ClickListener listener2) {
        listener = listener2;
    }
    public void changeData(List<Video> videoList2){
        for (Video video:videoList2){
            videoList.add(video);
        }
        notifyDataSetChanged();
    }
    public void changeFlag(boolean flag){
        isonLoad = flag;
    }
}
