package ar.com.plugtree.abfm.poc.parser;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import ar.com.plugtree.abfm.poc.domain.Location;
import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Skill;

public class ServiceDeliveryParser extends DomainParser<ServiceDelivery> {

    public ServiceDeliveryParser(InputStream origin) {
        super( origin );
    }

    @Override
    public ServiceDelivery read(String line) {
        String[] slices = line.split( "," );
        Location location = Location.valueOf( slices[0] );
        Set<Skill> neededSkills = new HashSet<Skill>();
        for ( int i = 1; i < slices.length; i++ ) {
            neededSkills.add( Skill.valueOf( slices[i] ) );
        }

        return new ServiceDelivery( location,
                                    neededSkills );
    }

}
