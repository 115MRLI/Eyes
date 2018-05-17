package eyes.video.base;


public interface BasePresenter<V extends BaseView> {

    void attachView(V baseView);

    void detachView();
}
