package eyes.video.ui.activity;



import eyes.video.R;
import eyes.video.base.BaseActivity;
import eyes.video.presenter.IHomePresenter;
import eyes.video.presenter.impl.HomePresenterImpl;
import eyes.video.ui.contract.MainActivityContract;

public class MainActivity extends BaseActivity implements MainActivityContract {
    private IHomePresenter presenter = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        presenter = new HomePresenterImpl();
        presenter.attachView(this);
        presenter.getMenu();

    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
