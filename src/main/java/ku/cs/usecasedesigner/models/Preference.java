package ku.cs.usecasedesigner.models;

public class Preference {
    private int strokeWidth;
    private String font;
    private int fontSize;
    private String theme;

    public Preference(int strokeWidth, String font, int fontSize, String theme) {
        this.strokeWidth = strokeWidth;
        this.font = font;
        this.fontSize = fontSize;
        this.theme = theme;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getTheme() {
        return theme;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
