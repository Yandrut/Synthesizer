package DAsynth;
import static org.lwjgl.openal.AL10.*;
public class OpenAlException extends RuntimeException {
    OpenAlException(int errorCode) {
        super("Internal " + (errorCode == AL_INVALID_NAME ? "INVALID NAME" : errorCode == AL_INVALID_ENUM ? "INVALID ENUM" : errorCode == AL_INVALID_VALUE ?
                "INVALID VALUE" : errorCode == AL_INVALID_OPERATION ? "INVALID OPERATION" : "UNKNOWN") + "OpenAL Exception.");
    }
}
