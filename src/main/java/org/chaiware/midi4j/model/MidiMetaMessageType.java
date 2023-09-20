package org.chaiware.midi4j.model;

public enum MidiMetaMessageType {
    SEQUENCE_NUMBER(0),
    TEXT_EVENT(1),
    COPYRIGHT_NOTICE(2),
    SEQUENCE_TRACK_NAME(3),
    INSTRUMENT_NAME(4),
    LYRIC(5),
    MARKER(6),
    CUE_POINT(7),
    MIDI_CHANNEL_PREFIX(32),
    END_OF_TRACK(47),
    SET_TEMPO(81),
    SMPTE_OFFSET(84),
    TIME_SIGNATURE(88),
    KEY_SIGNATURE(89),
    SEQUENCER_SPECIFIC(127),
    UNKNOWN(-1);

    private final int value;

    MidiMetaMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MidiMetaMessageType getByValue(int value) {
        for (MidiMetaMessageType type : values())
            if (type.getValue() == value)
                return type;

        return MidiMetaMessageType.UNKNOWN;
    }

    public String getReadableMetaName() {
        StringBuilder formattedName = new StringBuilder();
        for (String word : name().split("_"))
            formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");

        formattedName.append("\n").append("-".repeat(formattedName.length() - 2));
        return formattedName.toString();
    }

    /** Identifies the meta flags which represent Textual messages (Except for Lyrics which are textual but will be returned to the user using a special method) */
    public boolean isOfTextType() {
        return this == MidiMetaMessageType.SEQUENCE_TRACK_NAME
                || this == MidiMetaMessageType.TEXT_EVENT
                || this == MidiMetaMessageType.COPYRIGHT_NOTICE
                || this == MidiMetaMessageType.INSTRUMENT_NAME
                || this == MidiMetaMessageType.MARKER;
    }
}
