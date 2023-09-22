# midi4j
**Midi** Sound Files **Player** and **Meta** Parser


Java implementation of a Midi file Player & Midi Meta Info Parser Library.
This implementation is using vanilla Java classes without any 3rd party libraries.

If you just want to play a Midi file and don't want to delve into the midi classes (Like the Sequencer, Synthesizer, Tracks etc),
then just use the `Midi` class, point it to your Midi file and use the `play()` method to play it, `togglePause()` to
unqpause or navigate to any place in the Midi file `jumpToPosition(long milliseconds)`.

If you want to parse a Midi file, to read the MetaData written about it, from a Disclaimer, to copyright notice, through
titles and the track messages then just use the `MidiInfo` class
and call the relevant methods.

Methods names are self-explanatory, and JavaDocs exist - these are your friends.

**_midi4j_** was created with convenience and simplicity in mind, so it consists of only those two important files.

`Midi.java` - which is used to play/pause/jumpToPosition the Midi file.

`MidiInfo.java` - is used to parse the Meta Information from the Midi file (if midi info was indeed put into the Midi
file)


## Maven
`<dependency>`  
`    <groupId>org.chaiware</groupId>`  
`    <artifactId>midi4j</artifactId>`  
`    <version>1.0</version>`  
`</dependency>`  

## Examples

###### (A complete example with additional functionality can be found in the `Main.java` file)

#### Midi Player Example

`Midi midi = new Midi($PATH_TO_MIDI_FILE$);`  
`midi.play(); // Plays Asynchronously`  
`midi.jumpToPositionInMS(midi.getCurrentPositionInMS() + 50000); // Jumps forward`  
`while (midi.isPlaying())`  
`    Thread.sleep(250);`  
`midi.shutdown();`

#### Midi Info Example

`MidiInfo midiInfo = new MidiInfo($PATH_TO_MIDI_FILE$);`  
`Map<MidiMetaMessageType, String> metaInfo = midiInfo.getAllMetaInfo();`  
`for (Map.Entry<MidiMetaMessageType, String> entry : metaInfo.entrySet())`  
`    System.out.println(entry.getKey().getReadableMetaName() + "\n" + entry.getValue() + "\n\n");`  
