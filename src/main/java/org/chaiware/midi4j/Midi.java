package org.chaiware.midi4j;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import java.io.File;

/** Midi Class enabling to Parse a Midi file and play the song (with play/pause, and jumping to a specific position in the song) */
public class Midi {
    private final Sequencer sequencer = MidiSystem.getSequencer();
    private final Synthesizer synthesizer = MidiSystem.getSynthesizer();
    private boolean isPaused = false;

    public Midi(String pathToMidiFile) throws Exception {
        sequencer.open();
        synthesizer.open();
        Sequence sequence = MidiSystem.getSequence(new File(pathToMidiFile));
        sequencer.setSequence(sequence);
    }

    /** Main method - Plays the Midi file
     * Please note that it is Asynchronously playing */
    public void play() {
        sequencer.start();
    }

    /** Stops the currently playing Midi */
    public void stopPlay() {
        sequencer.stop();
    }

    /** Pauses/UnPauses the current Play of the Midi */
    public void togglePause() {
        try {
            if (isPlaying())
                sequencer.stop();
            else
                sequencer.start();

            isPaused = !isPaused;
        } catch (Exception e) {
            System.out.println("Failed PAUSE toggle: " + e.getMessage());
        }
    }

    /** @return Status of the Midi - is it currently playing ? */
    public boolean isPlaying() {
        return sequencer.isRunning();
    }

    /** @return Pause Status of the Midi - is it currently in Paused mode ? */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * @return current position in the Midi in microseconds
     */
    public long getCurrentPositionInMS() {
        return sequencer.getMicrosecondPosition();
    }

    /** Jumps to the argumented time position in the Midi file and will play from there
     * @param positionInMS The MicroSecond you want to navigate to */
    public void jumpToPositionInMS(long positionInMS) {
        sequencer.setMicrosecondPosition(positionInMS);
    }

    public void shutdown() {
        sequencer.stop();
        sequencer.close();
        synthesizer.close();
    }
}
