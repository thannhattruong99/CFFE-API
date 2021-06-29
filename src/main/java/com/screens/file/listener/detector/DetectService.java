package com.screens.file.listener.detector;

import com.util.FileHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.util.PathConstant.*;

public class DetectService {
    private final static String PERSONS = "PERSONS:";
//    PERSONS:
    private final static int SIZE = 9;

    private static String createHotSpotCommand(String inputFilePath, String outputFilePath){
        String command = "";
//      python version
        command += PYTHON38;
//        run file path
        command += " " + FileHelper.getOutProjectPath() + RUN_COUNT_PEOPLE_PATH;
//        protxt file path
        command += " " + PROTXT_ARGUMENT + " " + FileHelper.getOutProjectPath() + PROTXT_PATH;
//        count model path
        command += " " + MODEL_ARGUMENT + " " + FileHelper.getOutProjectPath() + COUNT_MODEL_PATH;
//        input video
        command += " " + INPUT_VIDEO_ARGUMENT + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFilePath;
//        output video
        command += " " + OUTPUT_VIDEO_ARGUMENT + " " + FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + outputFilePath;
        return command;
    }

    public static int countPerson(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        int numberOfPerson = 0;
        Runtime rt = Runtime.getRuntime();
        String command = createHotSpotCommand(inputFileName, outputFileName);
        System.out.println("COMMAND: "  + command);
        Process proc = rt.exec(command);
        numberOfPerson = readHotSpotConsole(proc);
        proc.isAlive();

        return numberOfPerson;
    }


    private String createDetectEmotionCommand(String inputFileName, String outputFileName){
        String command = "";
//        python version
        command += PYTHON38;
//        run file path
        command += " " + FileHelper.getOutProjectPath() + RUN_EMOTION_PATH;
//        input video
        command += " " + VIDEO_ARGUMENT + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFileName;
//       fps video
        command += " " + FPS_ARGUMENT + " " + DETECT_EMOTION_FPS;
//        output video
        command += " " + SAVE_ARGUMENT + " " + OUTPUT_VIDEO_PATH + outputFileName;
//        emotion model path
        command += " " + MODEL_ARGUMENT + " " + EMOTION_MODEL_PATH;
//        emotion weight path
        command += " " + WEIGHT_ARGUMENT + " " + EMOTION_WEIGHT_PATH;

        return command;
    }

    // LuanNM temp
    public static EmotionDTO countEmotion(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        EmotionDTO emotionDTO = new EmotionDTO();
        Runtime rt = Runtime.getRuntime();
        String command = createHotSpotCommand(inputFileName, outputFileName);
        Process proc = rt.exec(command);
        System.out.println("COMMAND: "  + command);

        emotionDTO = readEmotionConsole(proc);


        proc.isAlive();

        return emotionDTO;
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

    private static int readHotSpotConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        StringBuilder stringBuilder = new StringBuilder();

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            stringBuilder.append(s);
        }
// Read any errors from the attempted command
//        System.out.println("Here is the standard error of the command (if any):\n");
//        while ((s = stdError.readLine()) != null) {
//            System.out.println(s);
//            stringBuilder.append(s);
//        }

        return getHotSpotResult(stringBuilder);
    }

    public static int getHotSpotResult(StringBuilder stringBuilder){
        int index = stringBuilder.indexOf(PERSONS) + SIZE;
        stringBuilder.delete(0, index);
        return Integer.parseInt(String.valueOf(stringBuilder));
    }

    private static EmotionDTO readEmotionConsole(Process proc) throws IOException {
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

//        TODO: parse string tobe EmotionDTO

        return null;
    }
}
