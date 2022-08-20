package com.atichdev;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.XRLog;

import java.io.*;
import java.util.logging.Level;

public class ConvertJobProcess {

    private String errorMsg;
    private Exception exception;
    private PdfRendererBuilder builder;

    public ConvertJobProcess() {
        this.builder = new PdfRendererBuilder();
        this.builder.useFastMode();
        this.builder.usePdfUaAccessbility(true);

        // ignores Info messages
        XRLog.listRegisteredLoggers().forEach(logger -> XRLog.setLevel(logger, Level.WARNING));
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Exception getException() {
        return exception;
    }

    public boolean run(ConvertJob job) {
        this.errorMsg = null;
        FileOutputStream fos = this.createFileOutputStream(job.pdfPath);
        if (fos == null) {
            return false;
        }
        if (!this.writePdf(job.htmlPath, fos)) {
            this.closeFOS(fos, job.pdfPath);
            return false;
        }
        return this.closeFOS(fos, job.pdfPath);
    }

    private FileOutputStream createFileOutputStream(String pdfPath) {
        try {
            return new FileOutputStream(pdfPath);
        } catch (FileNotFoundException e) {
            this.errorMsg = "Output file \"" + pdfPath + "\" could not be created.";
            this.exception = e;
        }
        return null;
    }

    private boolean writePdf(String htmlPath, FileOutputStream fos) {
        this.builder.withFile(new File(htmlPath));
        this.builder.toStream(fos);
        try {
            this.builder.run();
            return true;
        } catch (Exception e) {
            this.errorMsg = "PDF could not be generated.";
            this.exception = e;
        }
        return false;
    }

    private boolean closeFOS(FileOutputStream fos, String pdfPath) {
        try {
            fos.close();
            return true;
        } catch (IOException e) {
            this.errorMsg = "Output file \"" + pdfPath + "\" could not be closed.";
            this.exception = e;
        }
        return false;
    }


}
