package ku.cs.usecasedesigner.models;

import javafx.scene.paint.Color;

public class Preference {
    private int strokeWidth;
    private Color strokeColor;
    private Color fontColor;
    private String theme;

    public Preference(String theme){
        this.strokeWidth = 1;
        this.strokeColor = Color.BLACK;
        this.fontColor = Color.BLACK;
        this.theme = theme;
    }

    public Preference(int strokeWidth, String theme) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = Color.BLACK;
        this.fontColor = Color.BLACK;
        this.theme = theme;
    }

    public Preference(int strokeWidth, Color strokeColor, Color fontColor, String theme){
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.fontColor = fontColor;
        this.theme = theme;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }


    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public String getTheme() {
        return theme;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
