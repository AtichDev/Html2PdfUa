package com.atichdev;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.gson.Gson;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.XRLog;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
        File htmlFile = new File(job.htmlPath);
        String baseUri = Paths.get(htmlFile.getAbsolutePath()).toUri().toString();
        String htmlContent = this.readFileContent(htmlFile);
        if (htmlContent == null) {
            this.closeFOS(fos, job.pdfPath);
            return false;
        }
        if (job.mustachePath != null) {
            htmlContent = this.parseMustacheTemplate(job.mustachePath, htmlContent);
            if (htmlContent == null) {
                this.closeFOS(fos, job.pdfPath);
                return false;
            }
        }
        if (!this.writePdf(htmlContent, baseUri, fos)) {
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

    private String readFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            this.errorMsg = "File " + file.getPath() + " could not be read.";
            this.exception = e;
        }
        return null;
    }

    private String parseMustacheTemplate(String jsonPath, String mustacheTemplate) {
        String jsonContent = this.readFileContent(new File(jsonPath));
        if (jsonContent == null) {
            return null;
        }
        Map<String, Object> gsonMap = new HashMap<String, Object>();
        try {
            Gson gson = new Gson();
            gsonMap = (Map<String, Object>) gson.fromJson(jsonContent, gsonMap.getClass());
        } catch (Exception e) {
            this.errorMsg = "Json content could not be parsed.";
            this.exception = e;
            return null;
        }

        try {
            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache mustache = mf.compile(new StringReader(mustacheTemplate), "template");
            StringWriter htmlWriter = new StringWriter();
            mustache.execute(htmlWriter, gsonMap);
            return htmlWriter.toString();
        } catch (Exception e) {
            this.errorMsg = "Mustache template could not be compiled.";
            this.exception = e;
        }
        return null;
    }

    private boolean writePdf(String htmlContent, String baseUri, FileOutputStream fos) {
//        this.builder.withFile(new File(htmlPath));
        this.builder.withHtmlContent(htmlContent, baseUri);
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
