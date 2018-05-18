package eyes.video.presenter.impl;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eyes.video.model.bean.Home;
import eyes.video.model.bean.Video;
import eyes.video.presenter.IHomePresenter;
import eyes.video.ui.contract.MainActivityContract;

public class HomePresenterImpl<T extends MainActivityContract> implements IHomePresenter<T> {
    private T baseView;

    final List<Home> homes = new ArrayList<>();
    final List<Video> videos = new ArrayList<>();


    @Override
    public void attachView(T baseView) {
        this.baseView = baseView;
    }

    @Override
    public void detachView() {
        baseView = null;
    }

    @Override
    public void getMenu() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://www.ananshe11.com").get();
                    Elements elements = doc.select("a");
                    for (int i = 0; i < elements.size(); i++) {
                        final Home home = new Home();
                        final Video video = new Video();
                        Document doc2 = Jsoup.parse(elements.get(i).html());//解析HTML字符串返回一个Document实现
                        Element link = doc2.select("img").first();//查找第一个a元素
                        if (link == null) {
                            home.setMenutitle(elements.get(i).text());
                            home.setMenuUrl(elements.get(i).attr("href"));
                            homes.add(home);
                        } else {
                            video.setImage(link.attr("src"));
                            video.setName(elements.get(i).text());
                            video.setUrl(elements.get(i).attr("href"));
                            videos.add(video);
                        }
                    }

                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    for (Home home1 : homes) {
                        Log.e("home", home1.toString());
                    }
                    for (Video video1 : videos) {
                        Log.e("video1", video1.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
