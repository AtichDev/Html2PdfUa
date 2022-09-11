package com.atichdev;

public class ConvertJob {

    public String mustachePath;
    public String htmlPath;
    public String pdfPath;

    @Override
    public String toString() {
        if (this.mustachePath != null) {
            return this.mustachePath + " > " + this.htmlPath + " > " + this.pdfPath;
        }
        return this.htmlPath + " > " + this.pdfPath;
    }
}
