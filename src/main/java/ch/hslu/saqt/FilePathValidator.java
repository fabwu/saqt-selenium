package ch.hslu.saqt;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * Validate if a given file path exists.
 */
public class FilePathValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        File file = new File(value);
        if (!file.exists() || file.isDirectory()) {
            throw new ParameterException("Die Datei konnte nicht gefunden werden");
        }
    }
}
