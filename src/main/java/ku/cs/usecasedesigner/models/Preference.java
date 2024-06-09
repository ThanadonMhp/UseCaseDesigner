package ku.cs.usecasedesigner.models;

public class Preference {
    private int strokeWidth;
    private String font;
    private int fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private String theme;

    public Preference(String theme){
        this.strokeWidth = 1;
        this.font = "Arial";
        this.fontSize = 12;
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.theme = theme;
    }

    public Preference(int strokeWidth, String font, int fontSize, String theme) {
        this.strokeWidth = strokeWidth;
        this.font = font;
        this.fontSize = fontSize;
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.theme = theme;
    }

    public Preference(int strokeWidth, String font, int fontSize, boolean bold, boolean italic, boolean underline, String theme) {
        this.strokeWidth = strokeWidth;
        this.font = font;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
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

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isUnderline() {
        return underline;
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
