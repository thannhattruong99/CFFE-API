package com.batches.events;

import com.util.FileHelper;
import com.util.GCPHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonHelper {
    private final static String RUN_PYTHON_SOURCE = "/Users/truongtn/Desktop/Desktop/HocTap/Semester8/Python/CoutingPeople/People-Counting-in-Real-Time/";
    private final static String PYTHON_3 = "python3";
    private final static String INPUT_OPTION = "--input";
    private final static String OUTPUT_OPTION = "--output";

    public static boolean countPerson(String inputVideoPath, String outputVideoPath) throws InterruptedException {
        try{
            Runtime rt = Runtime.getRuntime();
            String command = PYTHON_3 + " " + RUN_PYTHON_SOURCE + "Run.py" + " "
                    + "--prototxt "+ RUN_PYTHON_SOURCE + "mobilenet_ssd/MobileNetSSD_deploy.prototxt" +
                    " --model " + RUN_PYTHON_SOURCE + "mobilenet_ssd/MobileNetSSD_deploy.caffemodel" + " "
                    + INPUT_OPTION + " " + FileHelper.getResourcePath() + inputVideoPath
                    + " " + OUTPUT_OPTION + " " + FileHelper.getResourcePath() + outputVideoPath;
            System.out.println("COMMAND: "  + command);
            Process proc = rt.exec(command);
            readConsole(proc);
            if(proc.isAlive()){
                System.out.println("Con song day ne");
            }
            System.out.println("proc.waitFor(): " + proc.waitFor());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    private static void watch(final Process process) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void readConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

// Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

    }
}
