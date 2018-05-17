package eyes.video.ui.activity;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import eyes.video.R;
import eyes.video.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://www.ananshe11.com/").get();
                    Elements elements = doc.select("a");
                    for (int i= 0;i<elements.size();i++){
                        Log.i("mytag", "url:" + elements.get(i).attr("href"));
                        Log.i("mytag", "url:" + elements.get(i).text());
                        Document doc2 = Jsoup.parse( elements.get(i).html());//解析HTML字符串返回一个Document实现
                        Element link = doc2.select("img").first();//查找第一个a元素
                        if (link ==null){
                            Log.i("mytag", "url:没有图片" );
                        }else {
                            Log.i("mytag", "url:" +link.attr("src"));
                        }
                    }
//                    Elements burden = doc.select("a");
//                    //对于一个元素中的文本，可以使用Element.text()方法
//                    Log.i("mytag", "burden:" + burden.get(1).text());
                    //打印 <a>标签里面的title
                    Log.e("HTML：",elements.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
    }
}
