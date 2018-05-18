package eyes.video.presenter;


import eyes.video.base.BasePresenter;
import eyes.video.base.BaseView;

public interface IHomePresenter<T extends BaseView> extends BasePresenter<T> {

    void getMenu();
}
