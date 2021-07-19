package com.util;

public class PathConstant {
    public final static String RESOURCE_PATH = "/target/classes/";
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
    public static final String PROJECT_SERVER_PATH = "CapstoneAPI/target";
    public static final String PROJECT_LOCAL_PATH = "CapstoneAPI";

//    PYTHON COMMON OPTION
    public static String PYTHON38 = "python3";
    public static final String FPS_ARGUMENT = "--fps";
    public static final String MODEL_ARGUMENT = "--model";
    public static final String WEIGHT_ARGUMENT = "--weight";
    public static final String CONFIDENCE_ARGUMENT = "--confidence";

//    HOT SPOT
    public static final String HOTSPOT_RUN_PATH = "Counting-People/Run.py";
    public static final String HOTSPOT_PROTXT_PATH = "Counting-People/mobilenet_ssd/MobileNetSSD_deploy.prototxt";
    public static final String HOTSPOT_MODEL_PATH = "Counting-People/mobilenet_ssd/MobileNetSSD_deploy.caffemodel";
    public static final String HOTSPOT_PROTXT_ARGUMENT = "--prototxt";
    public static final String HOTSPOT_INPUT_ARGUMENT = "--input";
    public static final String HOTSPOT_OUTPUT_ARGUMENT = "--output";
    public static final String HOTSPOT_MAX_DISAPPEARED_ARGUMENT = "--max-disappeared";
    public static final String HOTSPOT_MAX_DISTANCE_ARGUMENT = "--max-distance";
    public static final String HOTSPOT_MAX_DISAPPEARED = "40";
    public static final String HOTSPOT_MAX_DISTANCE = "50";


//    EMOTION
    public static final String EMOTION_RUN_PATH = "Facial-Emotion-Recognition/video_detect_mtcnn_vgg_model.py";
    public static final String EMOTION_WEIGHT_PATH = "emotion_weight.h5";
    public static final String EMOTION_VIDEO_ARGUMENT = "--video";
    public static final String EMOTION_SAVE_ARGUMENT = "--save";
    public static final String EMOTION_CONFIDENCE = "0.5";
    public static final String EMOTION_FPS = "2";
}
