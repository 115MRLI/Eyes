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
import eyes.video.model.bean.Video;
import eyes.video.presenter.IFragmentPresenter;
import eyes.video.ui.contract.FragemntContract;
import eyes.video.utils.DBHelper;
import eyes.video.utils.EysUtils;

public class FragmentPresenterImpl<T extends FragemntContract> implements IFragmentPresenter<T> {
    private T baseView;
    List<Video> videos = new ArrayList<>();
    private String nexturl = "",lasturl = "";
    @Override
    public void attachView(T baseView) {
        this.baseView = baseView;
    }

    @Override
    public void detachView() {
        baseView = null;
    }

    @Override
    public void getVideo(final String url) {
        Log.e("下一页           ","http://www.ananshe11.com" + url);
        if (baseView != null) {
            if (EysUtils.isConnected(App.mContext)) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Document doc = Jsoup.connect("http://www.ananshe11.com" + url).get();
                            Elements elements = doc.select("a");
                            Elements neta = doc.select("div.pagination").select("a");
                            for (int i = 0; i < elements.size(); i++) {
                                final Video video = new Video();
                                Document doc2 = Jsoup.parse(elements.get(i).html());//解析HTML字符串返回一个Document实现
                                Element link = doc2.select("img").first();//查找第一个a元素
                                Element link2 = doc2.select("span").first();//查找第一个a元素
                                if (link != null) {
                                    video.setImage(link.attr("src"));
                                    video.setName(elements.get(i).text());
                                    video.setUrl(elements.get(i).attr("href"));
                                    video.setType(link2.text());
                                    Log.e("....", video.toString());
                                    videos.add(video);
                                }
                            }
                            for (int i = 0; i < neta.size(); i++){
                                if (neta.get(i).text().equals("下一页")){
                                    Log.e("下一页",neta.get(i).attr("href"));
                                   nexturl = neta.get(i).attr("href");
                                }
                                if (neta.get(i).text().equals("尾页")){
                                    Log.e("下一页",neta.get(i).attr("href"));
                                    lasturl = neta.get(i).attr("href");
                                }
                            }
                            handler.sendEmptyMessage(1);
//                            Log.e("下一页",neta.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                baseView.showToas("无网络请检查您的网络设置");
                try {
                    if (DBHelper.db.findAll(Video.class) != null) {
                        videos = DBHelper.db.findAll(Video.class);
                    }
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

                        if (DBHelper.db.findAll(Video.class) == null) {
                            for (Video video1 : videos) {
                                DBHelper.db.saveOrUpdate(video1);
                                Log.e("video1", video1.toString());
                            }
                        }
                        baseView.setVideos(videos);
                        baseView.geturl(nexturl,lasturl);
                        videos.clear();
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
