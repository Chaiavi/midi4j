package org.chaiware.midi4j.model;

/** Single Lyric, with the total time since the beginning of the song in Milliseconds and the Delta time since previous lyric was played */
public class Lyric {
    private final long timePositionFromBeginning;
    private final long deltaTime;
    private final String content;


    public Lyric(long totalTime, long deltaTime, String content) {
        this.timePositionFromBeginning = totalTime;
        this.deltaTime = deltaTime;
        this.content = content;
    }

    /**
     * @return Lyric word/s
     */
    public String getContent() {
        return content;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public long getTimePositionFromBeginning() {
        return timePositionFromBeginning;
    }
}
