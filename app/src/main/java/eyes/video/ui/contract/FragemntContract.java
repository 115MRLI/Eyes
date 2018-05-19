package eyes.video.ui.contract;

import java.util.List;

import eyes.video.base.BaseView;
import eyes.video.model.bean.Video;


public interface FragemntContract extends BaseView {
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

    /**
     *
     * @param nexturl
     * @param lasturl
     */
    void geturl(String nexturl ,String lasturl);
}
