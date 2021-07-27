package com.screens.file.listener.detector;

import com.util.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.util.PathConstant.*;

@Component
public class DetectService {
    private final static String PERSONS = "PERSONS:";
//    Size of persons
    private final static int SIZE = 8;
    private final static int NUMBER_EMOTION_TYPE = 7;
    private static int OUTPUT_ANGRY = 0;
    private static int OUTPUT_DISGUST = 1;
    private static int OUTPUT_FEAR = 2;
    private static int OUTPUT_HAPPY = 3;
    private static int OUTPUT_NEUTRAL = 4;
    private static int OUTPUT_SAD = 5;
    private static int OUTPUT_SURPRISE = 6;

    //python version
    @Value("${python.version}")
    private String python;

    //    PYTHON COMMON OPTION
    private static String FPS_ARGUMENT = "--fps";
    private static String MODEL_ARGUMENT = "--model";
    private static String WEIGHT_ARGUMENT = "--weight";
    private static String CONFIDENCE_ARGUMENT = "--confidence";

    //    HOT SPOT CONFIG VALUE
    @Value("${hotspot.run.path}")
    private String hotspotRunPath;
    @Value("${hotspot.prototxt.path}")
    private String hotspotPrototxtPath;
    @Value("${hotspot.model.path}")
    private String hotspotModelPath;
    @Value("${hotspot.max.disappeared}")
    private String hotspotMaxDisappeared;
    @Value("${hotspot.max.distance}")
    private String hotspotMaxDistance;
    @Value("${hotspot.confidence}")
    private String hotspotConfidence;
    @Value("${hotspot.skipframes}")
    private String skipframse;

    // HOTSPOT ARGUMENT
    private static String HOTSPOT_PROTOTXT_ARGUMENT = "--prototxt";
    private static String HOTSPOT_INPUT_ARGUMENT = "--input";
    private static String HOTSPOT_OUTPUT_ARGUMENT = "--output";
    private static String HOTSPOT_MAX_DISAPPEARED_ARGUMENT = "--maxDisappeared";
    private static String HOTSPOT_MAX_DISTANCE_ARGUMENT = "--maxDistance";
    private static String SKIP_FRAMES_ARGUMENT = "--skip_frames";

    //    EMOTION CONFIG VALUE
    @Value("${emotion.run.path}")
    public String emotionRunPath;
    @Value("${emotion.weight.path}")
    public String emotionWeightPath;
    @Value("${emotion.confidence}")
    public String emotionConfidence;
    @Value("${emotion.fps}")
    public String emotionFPS;
    //    EMOTION ARGUMENT
    public static String EMOTION_VIDEO_ARGUMENT = "--video";
    public static String EMOTION_SAVE_ARGUMENT = "--save";


    private String createHotSpotCommand(String inputFilePath, String outputFilePath){
        String command = "";
        //      python version
        command += python;
        //      run file path
        command += " " + FileHelper.getOutProjectPath() + hotspotRunPath;
        //      protxt file path
        command += " " + HOTSPOT_PROTOTXT_ARGUMENT + " " + FileHelper.getOutProjectPath() + hotspotPrototxtPath;
        //      count model path
        command += " " + MODEL_ARGUMENT + " " + FileHelper.getOutProjectPath() + hotspotModelPath;
        //      input video
        command += " " + HOTSPOT_INPUT_ARGUMENT + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFilePath;
        //      output video
        command += " " + HOTSPOT_OUTPUT_ARGUMENT + " " + FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + outputFilePath;
        //      max disappeared
        command += " " + HOTSPOT_MAX_DISAPPEARED_ARGUMENT + " " + hotspotMaxDisappeared;
        //      max distance
        command += " " + HOTSPOT_MAX_DISTANCE_ARGUMENT + " " + hotspotMaxDistance;
        //      confidence
        command += " " + CONFIDENCE_ARGUMENT + " " + hotspotConfidence;
        //      skip frames
        command += " " + SKIP_FRAMES_ARGUMENT + " " + skipframse;
        return command;
    }

    public int countPerson(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        String command = createHotSpotCommand(inputFileName, outputFileName);
        Process proc = rt.exec(command);
        return readHotSpotConsole(proc);
    }


    private String createDetectEmotionCommand(String inputFileName, String outputFileName){
        String command = "";
        //        python version
        command += python;
        //        run file path
        command += " " + FileHelper.getOutProjectPath() + emotionRunPath;
        //        input video
        command += " " + EMOTION_VIDEO_ARGUMENT + " " + FileHelper.getResourcePath() + INPUT_VIDEO_PATH + inputFileName;
        //       fps video
        command += " " + FPS_ARGUMENT + " " + emotionFPS;
        //      confidence
        command += " " + CONFIDENCE_ARGUMENT + " " + emotionConfidence;
        //        output video
        command += " " + EMOTION_SAVE_ARGUMENT + " " + FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + outputFileName;
        //        emotion weight path
        command += " " + WEIGHT_ARGUMENT + " " + FileHelper.getOutProjectPath() + emotionWeightPath;


        return command;
    }

    // LuanNM temp
    public EmotionDTO countEmotion(String inputFileName, String outputFileName) throws InterruptedException, IOException {
        EmotionDTO emotionDTO = null;
        Runtime rt = Runtime.getRuntime();
        String command = createDetectEmotionCommand(inputFileName, outputFileName);
        Process proc = rt.exec(command);
        emotionDTO = readEmotionConsole(proc);
        return emotionDTO;
    }

    private int readHotSpotConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        // Read the output from the command
        StringBuilder stringBuilder = new StringBuilder();

        String s = null;
        while ((s = stdInput.readLine()) != null) {
            stringBuilder.append(s);
        }

        return getHotSpotResult(stringBuilder);
    }

    public int getHotSpotResult(StringBuilder stringBuilder){
        int index = stringBuilder.indexOf(PERSONS) + SIZE;
        stringBuilder.delete(0, index);
        try {
            return Integer.parseInt(String.valueOf(stringBuilder).trim());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    private EmotionDTO readEmotionConsole(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        // Read the output from the command
        String outputDetect = null;
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            outputDetect = s;
        }

        // parse string tobe EmotionDTO
        if (StringUtils.isEmpty(outputDetect)){
            return null;
        }
        String[] parts = outputDetect.split(",");
        if (!validOutputEmotion(parts)) {
            return null;
        }
        return convertOutputDetectToEmotionDTO(parts);
    }

    private boolean validOutputEmotion(String[] parts){
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

    private EmotionDTO convertOutputDetectToEmotionDTO(String[] parts) {
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
