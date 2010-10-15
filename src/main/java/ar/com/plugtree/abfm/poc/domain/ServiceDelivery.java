package ar.com.plugtree.abfm.poc.domain;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class ServiceDelivery {

    private Location location;
    private Set<Skill> neededSkills;
    private Technician technician;

    public ServiceDelivery(Location location,
                           Set<Skill> neededSkills) {
        this.location = location;
        this.neededSkills = neededSkills;
    }

    public ServiceDelivery(ServiceDelivery serviceDelivery) {
        this( serviceDelivery.location,
              EnumSet.copyOf( serviceDelivery.neededSkills ) );
        if ( serviceDelivery.technician != null ) {
            setTechnician( new Technician( serviceDelivery.technician ) );
        }
    }

    public Location getLocation(){
        return this.location;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    @Override
    public String toString() {
        return "service in " + location.name()
                + " required " + Arrays.toString( neededSkills.toArray() );
    }

    public Set<Skill> getNeededSkills() {
        return neededSkills;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((neededSkills == null) ? 0 : neededSkills.hashCode());
        result = prime * result + ((technician == null) ? 0 : technician.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ServiceDelivery other = (ServiceDelivery) obj;
        if ( location != other.location ) return false;
        if ( neededSkills == null ) {
            if ( other.neededSkills != null ) return false;
        } else if ( !neededSkills.equals( other.neededSkills ) ) return false;
        if ( technician == null ) {
            if ( other.technician != null ) return false;
        } else if ( !technician.equals( other.technician ) ) return false;
        return true;
    }
}
