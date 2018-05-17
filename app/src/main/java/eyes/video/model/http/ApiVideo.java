package eyes.video.model.http;


import java.util.List;

import eyes.video.model.bean.Home;
import eyes.video.model.bean.Video;

public interface ApiVideo {
    /**
     * 菜单
     *
     * @param home
     */
    void menu(List<Home> home);

    /**
     * 首页推荐
     *
     * @param video
     */
    void homepage(List<Video> video);
}
