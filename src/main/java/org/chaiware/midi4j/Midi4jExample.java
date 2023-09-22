package org.chaiware.midi4j;

import org.chaiware.midi4j.model.Lyric;
import org.chaiware.midi4j.model.MidiMetaMessageType;

import javax.swing.*;
import java.util.Map;


/**
 * Simple Main Class showing off the Midi and MidiInfo capabilities, including showing lyrics when they exist
 */
public class Midi4jExample {
    public static void main(String[] args) throws Exception {
        // Choose a Midi file to play and show Meta info
        String midiFilePath = "Please choose a Midi file using the JFileChooser";
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            midiFilePath = fileChooser.getSelectedFile().getAbsolutePath();

        Midi midi = new Midi(midiFilePath);
        MidiInfo midiInfo = new MidiInfo(midiFilePath);
        System.out.println("Chosen Midi file: " + midiFilePath + " :: Duration: " + convertMicrosecondsToTime(midiInfo.getMidiLengthInMS()));

        // Print the Midi file Info
        System.out.println("\n\nWhen one looks for Midi Meta Info, he will generally query the 'Text Event' or the 'Track Name'");
        System.out.println(midiInfo.getMetaInfo(MidiMetaMessageType.TEXT_EVENT));
        System.out.println(midiInfo.getMetaInfo(MidiMetaMessageType.SEQUENCE_TRACK_NAME));

        System.out.println("\n\nSometimes one will want every textual information he can parse from the Midi File");
        Map<MidiMetaMessageType, String> metaInfo = midiInfo.getAllMetaInfo();
        for (Map.Entry<MidiMetaMessageType, String> entry : metaInfo.entrySet())
            System.out.println(entry.getKey().getReadableMetaName() + "\n" + entry.getValue() + "\n\n");

        // Play the midi file and show the lyrics (if they exist) in their specific time in the song
        midi.play(); // Asynchronously

        if (!midiInfo.getLyrics().isEmpty()) { // If lyrics exist then show them when they are supposed to be shown like karaoke
            for (Lyric lyric : midiInfo.getLyrics()) {
                Thread.sleep(lyric.getDeltaTime());
                System.out.print(lyric.getContent());
            }
        } else {
            while (midi.isPlaying())
                Thread.sleep(250);
        }

        midi.shutdown();
    }

    public static String convertMicrosecondsToTime(long microseconds) {
        long seconds = microseconds / 1000000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        long remainingMicroseconds = microseconds % 1000000;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, remainingSeconds, remainingMicroseconds / 1000);
    }
}
