package com.gensagames.samplewebrtc.engine.parameters;

/**
 * Created by GensaGames
 * GensaGames
 */
/**
 * Peer connection parameters.
 */
public class DataChannelParameters {
    public final boolean ordered;
    public final int maxRetransmitTimeMs;
    public final int maxRetransmits;
    public final String protocol;
    public final boolean negotiated;
    public final int id;

    public DataChannelParameters(boolean ordered, int maxRetransmitTimeMs, int maxRetransmits,
                                 String protocol, boolean negotiated, int id) {
        this.ordered = ordered;
        this.maxRetransmitTimeMs = maxRetransmitTimeMs;
        this.maxRetransmits = maxRetransmits;
        this.protocol = protocol;
        this.negotiated = negotiated;
        this.id = id;
    }

    public static DataChannelParameters getDefault () {
        return new DataChannelParameters(true, -1, -1, "", false, 1);
    }
}
