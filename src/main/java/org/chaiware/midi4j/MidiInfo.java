package org.chaiware.midi4j;

import org.chaiware.midi4j.model.Lyric;
import org.chaiware.midi4j.model.MidiMetaMessageType;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In charge of the Midi META Information like Song name, Artist, Disclaimer, Lyrics etc.
 */
public class MidiInfo {
    private final Sequence sequence;


    public MidiInfo(String pathToMidiFile) throws InvalidMidiDataException, IOException {
        sequence = MidiSystem.getSequence(new File(pathToMidiFile));
    }

    /**
     * @return all the Text MIDI Meta information like song name/artist Copyright notice etc (Except for Lyrics which have their own method)
     */
    public Map<MidiMetaMessageType, String> getAllMetaInfo() {
        Map<MidiMetaMessageType, String> metaMessages = new HashMap<>();

        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiMessage midiEventMessage = track.get(i).getMessage();

                if (midiEventMessage instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) midiEventMessage;
                    String data = new String(metaMessage.getData(), StandardCharsets.UTF_8);
                    if (!data.isBlank()) {
                        MidiMetaMessageType metaMessageType = MidiMetaMessageType.getByValue(metaMessage.getType());
                        if (metaMessageType.isOfTextType())
                            metaMessages.merge(metaMessageType, data, (a, b) -> (a + b + "\n"));
                    }
                }
            }
        }

        return metaMessages;
    }

    /**
     * Parse a specific MetaMessage from the Midi file
     */
    public String getMetaInfo(MidiMetaMessageType requestedMetaMessageType) {
        StringBuilder metaData = new StringBuilder();

        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiMessage midiEventMessage = track.get(i).getMessage();

                if (midiEventMessage instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) midiEventMessage;
                    String data = new String(metaMessage.getData(), StandardCharsets.UTF_8);
                    if (!data.isBlank()) {
                        MidiMetaMessageType metaMessageType = MidiMetaMessageType.getByValue(metaMessage.getType());
                        if (metaMessageType == requestedMetaMessageType)
                            metaData.append(data).append("\n");
                    }
                }
            }
        }


        return metaData.toString();
    }

    /**
     * @return Lyrics of the Midi file if they exist, Lyrics will return with the timing of each lyric section
     */
    public List<Lyric> getLyrics() {
        long songLength = getMidiLengthInMS();
        List<Lyric> lyrics = new ArrayList<>();

        long previousLyricPosition = 0;
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);

                if (event.getMessage() instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) event.getMessage();
                    String lyricWord = new String(metaMessage.getData(), StandardCharsets.UTF_8);
                    if (metaMessage.getType() == MidiMetaMessageType.LYRIC.getValue()) {
                        long timePosition = event.getTick() * songLength / sequence.getTickLength();
                        long delta = (event.getTick() - previousLyricPosition) * songLength / sequence.getTickLength() / 1000;
                        lyrics.add(new Lyric(timePosition, delta, lyricWord));
                        previousLyricPosition = event.getTick();
                    }
                }
            }
        }

        return lyrics;
    }


    /**
     * Returns midi length in MicroSeconds
     */
    public long getMidiLengthInMS() {
        return sequence.getMicrosecondLength();
    }
}
