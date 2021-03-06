package eyes.video.ui.contract;

import java.util.List;

import eyes.video.base.BaseView;
import eyes.video.model.bean.Home;
import eyes.video.model.bean.Video;

public interface MainActivityContract extends BaseView {
    /**
     * 设置菜单
     *
     * @param homes
     */
    void setMenu(List<Home> homes);

    /**
     * 设置推荐内容
     *
     * @param videos
     */
    void setVideos(List<Video> videos);

    /**
     * 提示内容
     *
     * @param str
     */
    void showToas(String str);
}
