package eyes.video.model.http;

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

public class AnalysisHtml {

    public void getMenu(final ApiVideo apiVideo) {
        final List<Home> homes = new ArrayList<>();
        final List<Video> videos = new ArrayList<>();
        final Home home = new Home();
        final Video video = new Video();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://www.ananshe11.com").get();
                    Elements elements = doc.select("a");
                    for (int i = 0; i < elements.size(); i++) {
//                            Log.i("mytag", "url:" + elements.get(i).attr("href"));
//                            Log.i("mytag", "url:" + elements.get(i).text());
                        Document doc2 = Jsoup.parse(elements.get(i).html());//解析HTML字符串返回一个Document实现
                        Element link = doc2.select("img").first();//查找第一个a元素
                        if (link == null) {
                            home.setMenutitle(elements.get(i).text());
                            home.setMenuUrl(elements.get(i).attr("href"));
                            homes.add(home);
                        } else {
                            Log.i("mytag", "url:" + link.attr("src"));
                            video.setImage(link.attr("src"));
                            video.setName(elements.get(i).text());
                            video.setUrl(elements.get(i).attr("href"));
                            videos.add(video);
                        }
                    }
                    apiVideo.homepage(videos);
                    apiVideo.menu(homes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
