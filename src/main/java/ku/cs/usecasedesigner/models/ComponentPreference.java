package ku.cs.usecasedesigner.models;

public class ComponentPreference {
    private int strokeWidth;
    private String font;
    private int fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private double positionID;

    public ComponentPreference(int strokeWidth, String font, int fontSize, double positionID) {
        this.strokeWidth = strokeWidth;
        this.font = font;
        this.fontSize = fontSize;
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.positionID = positionID;
    }

    public ComponentPreference(int strokeWidth, String font, int fontSize, boolean bold, boolean italic, boolean underline, double positionID) {
        this.strokeWidth = strokeWidth;
        this.font = font;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.positionID = positionID;
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

    public double getPositionID() {
        return positionID;
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

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void setPositionID(double positionID) {
        this.positionID = positionID;
    }

}
