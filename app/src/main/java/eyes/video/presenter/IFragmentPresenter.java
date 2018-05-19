package eyes.video.presenter;


import eyes.video.base.BasePresenter;
import eyes.video.base.BaseView;

public interface IFragmentPresenter<T extends BaseView> extends BasePresenter<T> {
    void getVideo(String url);
}
