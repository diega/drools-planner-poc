package ar.com.plugtree.abfm.poc.planner;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.FactHandle;
import org.drools.WorkingMemory;
import org.drools.planner.core.move.Move;

import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Technician;

public class SetNewTechnician
    implements
    Move {

    private ServiceDelivery serviceDelivery;
    private Technician      technician;

    public SetNewTechnician(ServiceDelivery serviceDelivery,
                            Technician technician) {
        this.serviceDelivery = serviceDelivery;
        this.technician = technician;
    }

    @Override
    public boolean isMoveDoable(WorkingMemory workingMemory) {
        return !serviceDelivery.getTechnician().equals( technician );
    }

    @Override
    public Move createUndoMove(WorkingMemory workingMemory) {
        return new SetNewTechnician( serviceDelivery,
                                     serviceDelivery.getTechnician() );
    }

    @Override
    public void doMove(WorkingMemory workingMemory) {
        FactHandle serviceDeliveryHandle = workingMemory.getFactHandle( serviceDelivery );
        serviceDelivery.setTechnician( technician );
        workingMemory.update( serviceDeliveryHandle,
                              serviceDelivery );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        else if ( o instanceof SetNewTechnician ) {
            SetNewTechnician other = (SetNewTechnician) o;
            return new EqualsBuilder()
                    .append( serviceDelivery,
                             other.serviceDelivery )
                    .append( technician,
                             other.technician )
                    .isEquals();
        } else return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append( serviceDelivery )
                .append( technician )
                .toHashCode();
    }

    @Override
    public String toString() {
        return serviceDelivery.toString() + " to " + technician.toString();
    }
}
