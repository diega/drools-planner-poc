package ar.com.plugtree.abfm.poc.parser;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.plugtree.abfm.poc.domain.Location;
import ar.com.plugtree.abfm.poc.domain.Skill;
import ar.com.plugtree.abfm.poc.domain.Technician;
import ar.com.plugtree.abfm.poc.domain.TrainingLevel;

public class TechnicianParser extends DomainParser<Technician> {

    private static Logger logger = LoggerFactory.getLogger( TechnicianParser.class );

    public TechnicianParser(InputStream origin) {
        super( origin );
    }

    @Override
    public Technician read(String line) {
        String[] slices = line.split( "," );
        Location location = Location.valueOf( slices[0] );
        TrainingLevel trainingLevel = TrainingLevel.valueOf( slices[1] );
        Boolean busy = Boolean.valueOf( slices[2] );
        Set<Skill> skills = new HashSet<Skill>();
        for(int i = 3 ; i < slices.length ; i++){
            skills.add( Skill.valueOf( slices[i] ));
        }

        Technician technician = new Technician( location,
                                                trainingLevel,
                                                busy,
                                                skills );
        logger.debug( "parsed: {}",
                      technician );
        return technician;
    }
}
