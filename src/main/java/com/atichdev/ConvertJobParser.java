package com.atichdev;

public class ConvertJobParser {
    public ConvertJob parseAppArguments(String arg) {
        String[] jobParts = arg.split(">", -1);
        if (jobParts.length < 2) {
            return null;
        }
        ConvertJob job = new ConvertJob();
        job.pdfPath = jobParts[jobParts.length - 1].trim();
        job.htmlPath = jobParts[jobParts.length - 2].trim();
        if(jobParts.length >= 3) {
            job.mustachePath = jobParts[jobParts.length - 3].trim();
        }
        return job;
    }
}
