package eyes.video.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eyes.video.R;
import eyes.video.base.BaseFragment;
import eyes.video.model.bean.Video;
import eyes.video.presenter.IFragmentPresenter;
import eyes.video.presenter.impl.FragmentPresenterImpl;
import eyes.video.ui.adapter.VideoAdpter;
import eyes.video.ui.contract.FragemntContract;
import eyes.video.utils.EysUtils;

public class TypeFragment extends BaseFragment implements FragemntContract {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private String url;
    private IFragmentPresenter presenter;
    private boolean isurl = true;
    private String nexturl, lasturl;
    private List<Video> videos = new ArrayList<>();
    private VideoAdpter videoAdpter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_layout;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        url = getArguments().getString("url");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        presenter = new FragmentPresenterImpl();
        presenter.attachView(this);
        presenter.getVideo(url);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoAdpter = new VideoAdpter(videos);
        recycler.setAdapter(videoAdpter);
        videoAdpter.setClickListener(new VideoAdpter.ClickListener() {
            @Override
            public void onItmeClick(Video bean) {

            }

            @Override
            public void onLoad() {
                if (nexturl != null) {
                    if (!nexturl.equals(lasturl)) {
                        presenter.getVideo(nexturl);
                    } else {
                        videoAdpter.changeFlag(false);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setVideos(List<Video> videos2) {
        videoAdpter.changeData(videos2);
    }

    @Override
    public void showToas(String str) {
        EysUtils.showToast(str);
    }

    @Override
    public void geturl(String nexturl, String lasturl) {
        this.nexturl = nexturl;
        if (isurl == true) {
            isurl = false;
            this.lasturl = lasturl;
        }
    }
}
