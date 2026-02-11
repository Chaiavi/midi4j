<p align="center">
  <h1 align="center">midi4j</h1>
  <p align="center">
    <em>MIDI Player & Meta Info Parser for Java</em>
  </p>
  <p align="center">
    <img src="https://img.shields.io/badge/Pure_Java-☕-orange?style=flat-square" alt="Pure Java"/>
    <img src="https://img.shields.io/badge/Zero_Dependencies-⚡-green?style=flat-square" alt="Zero Dependencies"/>
    <img src="https://img.shields.io/badge/version-1.0-blue?style=flat-square" alt="Version 1.0"/>
  </p>
</p>

---

## Overview

**midi4j** is a Java library for playing MIDI sound files and parsing their metadata — built entirely with vanilla Java classes, **no third-party libraries required**.

If you just want to play a MIDI file without delving into low-level classes like `Sequencer`, `Synthesizer`, or `Tracks`, simply use the `Midi` class — point it to your file, call `play()`, `togglePause()`, or navigate with `jumpToPosition(long milliseconds)`.

If you want to read metadata from a MIDI file — disclaimers, copyright notices, titles, track messages — use the `MidiInfo` class and call the relevant methods.

> 💡 Method names are self-explanatory and JavaDocs are provided — they're your best friends when exploring the API.

---

## Core Components

**midi4j** was created with convenience and simplicity in mind, consisting of just two core files:

| File | Purpose |
|:-----|:--------|
| ▶️ **`Midi.java`** | Play, pause, and navigate MIDI files (`play()`, `togglePause()`, `jumpToPosition()`) |
| 🔍 **`MidiInfo.java`** | Parse metadata from MIDI files (disclaimers, copyrights, titles, track messages) |

---

## Maven

```xml
<dependency>
    <groupId>org.chaiware</groupId>
    <artifactId>midi4j</artifactId>
    <version>1.0</version>
</dependency>
```

---

## Examples

> A complete example with additional functionality can be found in the **`Midi4jExample.java`** file.

### ▶️ Midi Player

```java
Midi midi = new Midi($PATH_TO_MIDI_FILE$);

midi.play();  // Plays Asynchronously

midi.jumpToPositionInMS(midi.getCurrentPositionInMS() + 50000);  // Jump forward

while (midi.isPlaying())
    Thread.sleep(250);

midi.shutdown();
```

### 🔍 Midi Info Parser

```java
MidiInfo midiInfo = new MidiInfo($PATH_TO_MIDI_FILE$);

Map<MidiMetaMessageType, String> metaInfo = midiInfo.getAllMetaInfo();

for (Map.Entry<MidiMetaMessageType, String> entry : metaInfo.entrySet())
    System.out.println(
        entry.getKey().getReadableMetaName() + "\n" +
        entry.getValue() + "\n\n"
    );
```

---

## 💾 Project Using midi4j

**[FloppyMidiPlayer](https://github.com/Chaiavi/FloppyMidiPlayer)** — A simple project that uses midi4j to play MIDI files from floppy diskettes. It scans the diskette, plays all MIDI files it finds, and enables navigation, pausing, and viewing MetaInfo — all powered by midi4j.
