package ar.com.plugtree.abfm.poc.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class DomainParser<T> {

    private InputStream parseStream;

    public DomainParser(InputStream origin) {
        this.parseStream = origin;
    }

    public List<T> parse() throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( parseStream ) );
        List<T> parsedElements = new ArrayList<T>();
        String currentLine;
        while ( (currentLine = reader.readLine()) != null ) {
            parsedElements.add( read( currentLine ) );
        }
        return parsedElements;
    }

    public abstract T read(String line);

}
