package com.screens.file.listener.detector;

import com.util.FileHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.util.PathConstant.*;

public class DetectService {
    private final static String PERSONS = "PERSONS:";
//    PERSONS:
    private final static int SIZE = 8;
    private final static int NUMBER_EMOTION_TYPE = 7;
    private final static int OUTPUT_ANGRY = 0;
    private final static int OUTPUT_DISGUST = 1;
    private final static int OUTPUT_FEAR = 2;
    private final static int OUTPUT_HAPPY = 3;
    private final static int OUTPUT_NEUTRAL = 4;
    private final static int OUTPUT_SAD = 5;
    private final static int OUTPUT_SURPRISE = 6;

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
        // System.out.println("COMMAND: "  + command);
        Process proc = rt.exec(command);
        numberOfPerson = readHotSpotConsole(proc);
        proc.isAlive();

        return numberOfPerson;
    }


    private static String createDetectEmotionCommand(String inputFileName, String outputFileName){
        String command = "";
        //        python version
        command += PYTHON38;
        //        run file path
        command += " " + FileHelper.getOutProjectPath() + RUN_EMOTION_PATH;
        //        input video
        command += " " + VIDEO_ARGUMENT + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFileName;
        //       fps video
        command += " " + FPS_ARGUMENT + " " + DETECT_EMOTION_FPS;
        //      confidence
        command += " " + CONFIDENCE_ARGUMENT + " " + CONFIDENCE;
        //        output video
        command += " " + SAVE_ARGUMENT + " " + FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + outputFileName;
        //        emotion weight path
        command += " " + WEIGHT_ARGUMENT + " " + FileHelper.getOutProjectPath() + EMOTION_WEIGHT_PATH;


        return command;
    }

    // LuanNM temp
    public static EmotionDTO countEmotion(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        EmotionDTO emotionDTO = null;
        Runtime rt = Runtime.getRuntime();
        String command = createDetectEmotionCommand(inputFileName, outputFileName);
        Process proc = rt.exec(command);
        // System.out.println("COMMAND: "  + command);
        emotionDTO = readEmotionConsole(proc);
        proc.isAlive();
        return emotionDTO;
    }

    private static int readHotSpotConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        // System.out.println("Here is the standard output of the command:\n");
        StringBuilder stringBuilder = new StringBuilder();

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            // System.out.println(s);
            stringBuilder.append(s);
        }
        // Read any errors from the attempted command
        //        // System.out.println("Here is the standard error of the command (if any):\n");
        //        while ((s = stdError.readLine()) != null) {
        //            // System.out.println(s);
        //            stringBuilder.append(s);
        //        }

        return getHotSpotResult(stringBuilder);
    }

    public static int getHotSpotResult(StringBuilder stringBuilder){
        int index = stringBuilder.indexOf(PERSONS) + SIZE;
        stringBuilder.delete(0, index);
        try {
            return Integer.parseInt(String.valueOf(stringBuilder).trim());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    private static EmotionDTO readEmotionConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        // System.out.println("Here is the standard output of the command:\n");
        String outputDetect = null;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            outputDetect = s;
        }

        // Read any errors from the attempted command
        // System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            // System.out.println(s);
        }

        // parse string tobe EmotionDTO
        // System.out.println("output detect is : " + outputDetect);
        if (StringUtils.isEmpty(outputDetect)){
            return null;
        }
        String[] parts = outputDetect.split(",");
        if (!validOutputEmotion(parts)) {
            return null;
        }
        return convertOutputDetectToEmotionDTO(parts);
    }

    private static boolean validOutputEmotion(String[] parts){
        if (parts.length != NUMBER_EMOTION_TYPE) {
            return false;
        }
        try {
            for (String item : parts) {
                Integer.parseInt(item);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static EmotionDTO convertOutputDetectToEmotionDTO(String[] parts) {
        EmotionDTO emotionDTO = new EmotionDTO();
        emotionDTO.setNumberOfAngry(Integer.parseInt(parts[OUTPUT_ANGRY]));
        emotionDTO.setNumberOfDisgust(Integer.parseInt(parts[OUTPUT_DISGUST]));
        emotionDTO.setNumberOfFear(Integer.parseInt(parts[OUTPUT_FEAR]));
        emotionDTO.setNumberOfHappy(Integer.parseInt(parts[OUTPUT_HAPPY]));
        emotionDTO.setNumberOfSad(Integer.parseInt(parts[OUTPUT_SAD]));
        emotionDTO.setNumberOfSurprise(Integer.parseInt(parts[OUTPUT_SURPRISE]));
        emotionDTO.setNumberOfNeutral(Integer.parseInt(parts[OUTPUT_NEUTRAL]));
        return emotionDTO;
    }
}
