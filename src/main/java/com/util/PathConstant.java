package com.util;

public class PathConstant {
    public final static String RESOURCE_PATH = "/classes/";
    public final static String IMAGE_FOLDER_CLOUD = "images/";
    public final static String IMAGE_FOLDER_SERVER = "images/";
    public final static String VIDEO_FOLDER_CLOUD = "videos/";
//    public final static String VIDEO_FOLDER_SERVER = "videos/";
    public static final String ADMIN_AUTHORITY_PATH = "authorities/Admin.properties";
    public static final String MANAGER_AUTHORITY_PATH = "authorities/Manager.properties";
    public static final String APPLICATION_AUTHORITY_PATH = "authorities/Application.properties";
    public static final String GUEST_AUTHORITY_PATH = "authorities/Guest.properties";
    public static final String INPUT_VIDEO_PATH = "videos/input/";
    public static final String OUTPUT_VIDEO_PATH = "videos/";
    public static final String MSG_RELATIVE_PATH = "messages/msg";
    public static final String REXP_RESOURCE_PATH = "/resources/**";
    public static final String REXP_ALL_PATH = "/**";
    public static final String DEFAULT_INCLUDE_PATTERN = "http://35.240.143.111:9091/*";
    public static final String LOCAL_PATTERN = "http://localhost:9091/*";
    public static final String PROJECT_NAME = "CapstoneAPI";

//    PYTHON COMMON OPTION
    public static String PYTHON38 = "python3.8";
    public static final String FPS_ARGUMENT = "--fps";
    public static final String MODEL_ARGUMENT = "--model";
    public static final String WEIGHT_ARGUMENT = "--weight";

//    HOT SPOT
    public static final String PROTXT_ARGUMENT = "--prototxt";
    public static final String RUN_COUNT_PEOPLE_PATH = "Counting-People/Run.py";
    public static final String PROTXT_PATH = "Counting-People/mobilenet_ssd/MobileNetSSD_deploy.prototxt";
    public static final String COUNT_MODEL_PATH = "Counting-People/mobilenet_ssd/MobileNetSSD_deploy.caffemodel";
    public static final String INPUT_VIDEO_ARGUMENT = "--input";
    public static final String OUTPUT_VIDEO_ARGUMENT = "--output";

//    EMOTION
    public static final String RUN_EMOTION_PATH = "realtime_emotion_detection/videoOffline.py";
    public static final String VIDEO_ARGUMENT = "--video";
    public static final String SAVE_ARGUMENT = "--save";
    public static final String EMOTION_WEIGHT_PATH = "realtime_emotion_detection/fer.h5";
    public static final String EMOTION_MODEL_PATH = "realtime_emotion_detection/fer.json";
    public static final int DETECT_EMOTION_FPS = 2;

}
