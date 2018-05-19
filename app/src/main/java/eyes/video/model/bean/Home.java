package eyes.video.model.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "classify")
public class Home {
    @Column(name = "_id", isId = true, autoGen = false)
    private int id;
    @Column(name = "menutitle")
    private String menutitle;
    @Column(name = "menuUrl")
    private String menuUrl;

    public String getMenutitle() {
        return menutitle;
    }

    public void setMenutitle(String menutitle) {
        this.menutitle = menutitle;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Home{" +
                "menutitle='" + menutitle + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                '}';
    }
}
