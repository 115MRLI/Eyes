package eyes.video.model.bean;


public class Home {
    private String menutitle;
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

    @Override
    public String toString() {
        return "Home{" +
                "menutitle='" + menutitle + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                '}';
    }
}
