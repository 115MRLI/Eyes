package eyes.video.presenter.impl;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eyes.video.app.App;
import eyes.video.model.bean.Home;
import eyes.video.model.bean.Video;
import eyes.video.presenter.IHomePresenter;
import eyes.video.ui.contract.MainActivityContract;
import eyes.video.utils.DBHelper;
import eyes.video.utils.EysUtils;

public class HomePresenterImpl<T extends MainActivityContract> implements IHomePresenter<T> {
    private T baseView;

    List<Home> homes = new ArrayList<>();
    List<Video> videos = new ArrayList<>();


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
        if (baseView != null) {
            if (EysUtils.isConnected(App.mContext)) {
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
                                Element link2 = doc2.select("span").first();//查找第一个a元素

                                if (link == null) {
                                    home.setId(i);
                                    home.setMenutitle(elements.get(i).text());
                                    home.setMenuUrl(elements.get(i).attr("href"));
                                    homes.add(home);
                                } else {
                                    video.setImage(link.attr("src"));
                                    video.setName(elements.get(i).text());
                                    video.setUrl(elements.get(i).attr("href"));
                                    video.setType(link2.text());
                                    videos.add(video);
                                }
                            }
//                            Log.e("a::::::",elements.toString());
                            handler.sendEmptyMessage(1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                baseView.showToas("无网络请检查您的网络设置");
                try {
                    if (DBHelper.db.findAll(Home.class) != null) {
                        homes = DBHelper.db.findAll(Home.class);
                    }
                    if (DBHelper.db.findAll(Video.class) != null) {
                        videos = DBHelper.db.findAll(Video.class);
                    }
                    baseView.setMenu(homes);
                    baseView.setVideos(videos);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        if (DBHelper.db.findAll(Home.class) == null) {
                            for (Home home1 : homes) {
                                DBHelper.db.saveOrUpdate(home1);
                                Log.e("video1", home1.toString());
                            }
                        }

                        if (DBHelper.db.findAll(Video.class) == null) {
                            for (Video video1 : videos) {
                                DBHelper.db.saveOrUpdate(video1);
                                Log.e("video1", video1.toString());
                            }
                        }
                        baseView.setMenu(homes);
                        baseView.setVideos(videos);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }


                    break;
                default:
                    break;
            }
        }
    };
}
