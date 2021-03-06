package com.gensagames.samplewebrtc.engine.parameters;

/**
 * Created by GensaGames
 * GensaGames
 */

@SuppressWarnings("WeakerAccess")
public class Configs {

    public static final String GOOGLE_STUN_URI = "stun:stun.l.google.com:19302";
    public static final String GOOGLE_STUN_URI_1 = "stun:stun1.l.google.com:19302";
    public static final String SIPPHONE_STUN_URI = "stun:stun01.sipphone.com";
    public static final String VIDEO_TRACK_ID = "ARDAMSv0";
    public static final String AUDIO_TRACK_ID = "ARDAMSa0";
    public static final String VIDEO_TRACK_TYPE = "video";
    public static final String TAG = "PCRTCClient";
    public static final int AUDIO_START_BITRATE = 32;
    public static final String CODEC_DEFAULT = "OPUS";
    public static final String VIDEO_CODEC_VP8 = "VP8";
    public static final String VIDEO_CODEC_VP9 = "VP9";
    public static final String VIDEO_CODEC_H264 = "H264";
    public static final String VIDEO_CODEC_H264_BASELINE = "H264 Baseline";
    public static final String VIDEO_CODEC_H264_HIGH = "H264 High";
    public static final String AUDIO_CODEC_OPUS = "opus";
    public static final String AUDIO_CODEC_ISAC = "ISAC";
    public static final String VIDEO_CODEC_PARAM_START_BITRATE = "x-google-start-bitrate";
    public static final String VIDEO_FLEXFEC_FIELDTRIAL = "WebRTC-FlexFEC-03/Enabled/";
    public static final String VIDEO_VP8_INTEL_HW_ENCODER_FIELDTRIAL = "WebRTC-IntelVP8/Enabled/";
    public static final String VIDEO_H264_HIGH_PROFILE_FIELDTRIAL =
            "WebRTC-H264HighProfile/Enabled/";
    public static final String AUDIO_CODEC_PARAM_BITRATE = "maxaveragebitrate";
    public static final String AUDIO_ECHO_CANCELLATION_CONSTRAINT = "googEchoCancellation";
    public static final String AUDIO_AUTO_GAIN_CONTROL_CONSTRAINT = "googAutoGainControl";
    public static final String AUDIO_HIGH_PASS_FILTER_CONSTRAINT = "googHighpassFilter";
    public static final String AUDIO_NOISE_SUPPRESSION_CONSTRAINT = "googNoiseSuppression";
    public static final String AUDIO_LEVEL_CONTROL_CONSTRAINT = "levelControl";
    public static final String DTLS_SRTP_KEY_AGREEMENT_CONSTRAINT = "DtlsSrtpKeyAgreement";
    public static final int HD_VIDEO_WIDTH = 1280;
    public static final int HD_VIDEO_HEIGHT = 720;
    public static final int DEFAULT_VIDEO_BITRATE = 1700;
    public static final int BPS_IN_KBPS = 1000;
    public static final int DEFAULT_FPS = 30;
}
