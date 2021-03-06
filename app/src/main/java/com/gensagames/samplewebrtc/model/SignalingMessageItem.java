package com.gensagames.samplewebrtc.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gensagames.samplewebrtc.engine.VoIPEngineService;

import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GensaGames
 * GensaGames
 */

public class SignalingMessageItem implements Serializable {

    private String action = VoIPEngineService.ACTION_IDLE;
    private String userName;
    private String additionalContent;
    private MessageType msgType;

    /**
     * Specific only for WebRTC usage
     */
    private long peerSessionId;
    private SerializableSdp workingSdp;
    private List<SerializableIceCandidate> candidates;


    public enum MessageType {
        NONE,
        SDP_EXCHANGE,
        CANDIDATES
    }

    public SignalingMessageItem(@NonNull String userName, long peerSessionId,
                                @NonNull MessageType msgType, @Nullable List<IceCandidate> candidates,
                                @Nullable SessionDescription workingSdp, @Nullable String additionalContent) {
        this.userName = userName;
        this.peerSessionId = peerSessionId;
        this.msgType = msgType;

        if (candidates != null && !candidates.isEmpty()) {
            this.candidates = new ArrayList<>();
            for (IceCandidate candidate : candidates) {
                this.candidates.add(SerializableIceCandidate
                        .fromAnotherIceCandidate(candidate));
            }
        }

        this.additionalContent = additionalContent;
        this.workingSdp = workingSdp == null ? null
                : SerializableSdp.fromAnotherSdp(workingSdp);
    }

    public SignalingMessageItem(@NonNull String userName, long peerSessionId,
                                @NonNull MessageType msgType, @Nullable SessionDescription workingSdp,
                                @Nullable String additionalContent) {
        this(userName, peerSessionId, msgType, null, workingSdp, additionalContent);
    }

    public List<IceCandidate> getCandidates() {
        if (candidates != null && !candidates.isEmpty()) {
            List<IceCandidate> candidates = new ArrayList<>();
            for (SerializableIceCandidate candidate : this.candidates) {
                candidates.add(new IceCandidate(candidate.sdpMid,
                        candidate.sdpMLineIndex, candidate.sdp));
            }
            return candidates;
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public long getPeerSessionId() {
        return peerSessionId;
    }

    public MessageType getMessageType() {
        return msgType;
    }

    public SessionDescription getWorkingSdp() {
        return workingSdp == null ? null :
                new SessionDescription(workingSdp.type, workingSdp.description);
    }

    public String getAdditionalContent() {
        return additionalContent;
    }

    @NonNull
    public String getAction() {
        return action;
    }

    public void setAction(@NonNull String action) {
        this.action = action;
    }

    /**
     * Helper class, to work with SDP, as
     * Serializable object
     */


    @SuppressWarnings("WeakerAccess")
    public static class SerializableSdp implements Serializable {
        private SessionDescription.Type type;
        private String description;

        public SerializableSdp(SessionDescription.Type type, String description) {
            this.type = type;
            this.description = description;
        }

        public static SerializableSdp fromAnotherSdp (SessionDescription description) {
            return new SerializableSdp(description.type, description.description);
        }
    }

    /**
     * Helper class, to work with IceCandidate, as
     * Serializable object
     */

    @SuppressWarnings("WeakerAccess")
    public static class SerializableIceCandidate implements Serializable {
        private final String sdpMid;
        private final int sdpMLineIndex;
        private final String sdp;
        private final String serverUrl;

        public SerializableIceCandidate(String sdpMid, int sdpMLineIndex, String sdp) {
            this.sdpMid = sdpMid;
            this.sdpMLineIndex = sdpMLineIndex;
            this.sdp = sdp;
            this.serverUrl = "";
        }

        private SerializableIceCandidate(String sdpMid, int sdpMLineIndex, String sdp, String serverUrl) {
            this.sdpMid = sdpMid;
            this.sdpMLineIndex = sdpMLineIndex;
            this.sdp = sdp;
            this.serverUrl = serverUrl;
        }
        public static SerializableIceCandidate fromAnotherIceCandidate (IceCandidate candidate) {
            return new SerializableIceCandidate(candidate.sdpMid, candidate.sdpMLineIndex,
                    candidate.sdp, candidate.serverUrl);
        }
    }


    /**
     * It's just helper function, for representation
     * JSON in human readable format for TextView.
     */
    public static String formatString(String text) {

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;
                default:
                    json.append(letter);
                    break;
            }
        }
        return json.toString();
    }
}
