package com.atichdev;


public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("[Error] Please specify at least one argument in the following way: \"path/to/your.html > path/to/output.pdf\".\n");
            return;
        }

        ConvertJobParser jobParser = new ConvertJobParser();
        ConvertJobProcess jobProcess = new ConvertJobProcess();
        for (String arg : args) {
            System.out.println("[Info][" + arg + "] Start.\n");
            ConvertJob job = jobParser.parseAppArguments(arg);
            if (job == null) {
                System.out.println("[Error][" + arg + "] Argument could not be read.\n");
                System.out.println("[Info][" + arg + "] End.\n");
                continue;
            }
            if (jobProcess.run(job)) {
                System.out.println("[Info][" + arg + "] Successfully created.\n");
            } else {
                System.out.println("[Error][" + arg + "] " + jobProcess.getErrorMsg());
                System.out.println(jobProcess.getException().getMessage() + "\n");
            }
            System.out.println("[Info][" + arg + "] End.\n");
        }
    }

}
