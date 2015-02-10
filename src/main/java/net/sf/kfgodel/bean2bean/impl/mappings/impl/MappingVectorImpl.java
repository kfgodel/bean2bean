package net.sf.kfgodel.bean2bean.impl.mappings.impl;

import ar.com.kfgodel.diamond.api.equals.TokenIdentifiable;
import ar.com.kfgodel.diamond.impl.equals.ImmutableIdentityParts;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;

/**
 * This type represents a pair of objects indicating a mapping direction
 * Created by kfgodel on 10/02/15.
 */
public class MappingVectorImpl implements MappingVector, TokenIdentifiable {
    
    private ImmutableIdentityParts identityToken;
    
    private Object source;
    private Object destination;
    
    @Override
    public Object getSourceObject() {
        return source;
    }

    @Override
    public Object getDestinationObject() {
        return destination;
    }

    public static MappingVectorImpl create(Object source, Object destination) {
        MappingVectorImpl vector = new MappingVectorImpl();
        vector.source = source;
        vector.destination = destination;
        vector.identityToken = ImmutableIdentityParts.create(source, destination);
        return vector;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof MappingVectorImpl)){
            return false;
        }
        MappingVectorImpl other = (MappingVectorImpl) obj;
        return this.identityToken.equals(other.getIdentityToken());
    }

    @Override
    public int hashCode() {
        return identityToken.hashCode();
    }

    @Override
    public Object getIdentityToken() {
        return identityToken;
    }

    @Override
    public String toString() {
        return "[" + source + " -> " + destination + "]";
    }
}
