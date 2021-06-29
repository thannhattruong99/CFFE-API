package com.listeners.events;

import com.util.FileHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.util.PathConstant.*;

public class PythonHelper {
    private static final String INPUT_VIDEO_OPTION = "--input";
    private static final String OUTPUT_VIDEO_OPTION = "--output";
    private static final String PROTXT_OPTION = "--prototxt";
    private static final String COUNT_MODEL_OPTION = "--model";
    private static String PYTHON38 = "python3.8";

    private static String createHotSpotCommand(String inputFilePath, String outputFilePath){
        String command = "";
//      python version
        command += PYTHON38;
//        run file path
        command += " " + FileHelper.getOutProjectPath() + RUN_COUNT_PEOPLE_PATH;
//        protxt file path
        command += " " + PROTXT_OPTION + " " + FileHelper.getOutProjectPath() + PROTXT_PATH;
//        count model path
        command += " " + COUNT_MODEL_OPTION + " " + FileHelper.getOutProjectPath() + COUNT_MODEL_PATH;
//        input option
        command += " " + INPUT_VIDEO_OPTION + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFilePath;
//        output option
        command += " " + OUTPUT_VIDEO_OPTION + " " + FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + outputFilePath;
        return command;
    }

    public static int countPerson(String inputFileName, String outputFileName) throws InterruptedException, IOException {

        int numberOfPerson = 0;
        Runtime rt = Runtime.getRuntime();
        String command = createHotSpotCommand(inputFileName, outputFileName);
        System.out.println("COMMAND: "  + command);
        Process proc = rt.exec(command);
        numberOfPerson = readConsole(proc);
        proc.isAlive();

        return numberOfPerson;
    }

    // LuanNM temp
    public static int countEmotion(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        int numberOfPerson = 0;

        return numberOfPerson;
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

    private static int readConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        int numberOfPerson = 0;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            numberOfPerson = Integer.parseInt(s);
        }
//        int numberOfPerson = Integer.parseInt(s);
// Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        return numberOfPerson;
    }
}
