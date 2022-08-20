package com.atichdev;

public class ConvertJob {
    public String htmlPath;
    public String pdfPath;

    @Override
    public String toString() {
        return this.htmlPath + " > " + this.pdfPath;
    }
}
