package mobile.indomarco.com.migrasiHH.model;

public class MainMenuModel {

    int id, icon;
    String title,subtitle;

    public MainMenuModel(int id, int icon, String title, String subtitle) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
