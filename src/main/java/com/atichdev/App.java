package com.atichdev;


public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("[Error] Please specify at least one argument in the following way: \"path/to/your.html > path/to/output.pdf\".\n");
            return;
        }

        ConvertJobParser jobParser = new ConvertJobParser();
        ConvertJobProcess jobProcess = new ConvertJobProcess();
        for (String arg : args) {
            ConvertJob job = jobParser.parseAppArguments(arg);
            if (job == null) {
                System.err.println("[Error][" + arg + "] Argument could not be read.\n");
                continue;
            }
            if (jobProcess.run(job)) {
                System.out.println("[Success][" + arg + "] Done.\n");
            } else {
                System.err.println("[Error][" + arg + "] " + jobProcess.getErrorMsg());
                System.err.println(jobProcess.getException().getMessage() + "\n");
            }
        }
    }

}
