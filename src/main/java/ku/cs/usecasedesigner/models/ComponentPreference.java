package ku.cs.usecasedesigner.models;

import javafx.scene.paint.Color;

public class ComponentPreference {
    private int strokeWidth;
    private Color strokeColor;
    private Color fontColor;
    private String type;
    private int ID;

    public ComponentPreference(int strokeWidth, Color strokeColor, Color fontColor, String type, int ID){
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.fontColor = fontColor;
        this.type = type;
        this.ID = ID;
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

    public String getType() {
        return type;
    }

    public int getID() {
        return ID;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
