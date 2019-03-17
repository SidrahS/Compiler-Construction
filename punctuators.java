package punctuators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class punctuators {

    private final List<Character> punc = Arrays.asList(';',',', ':', '.', '(', ')', '{', '}', '[', ']', '\'', '"', '|', '=');
    
    public List<Character> getPunct() {
        return this.punc;
    }
}
