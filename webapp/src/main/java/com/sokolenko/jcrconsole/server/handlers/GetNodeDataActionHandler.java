package com.sokolenko.jcrconsole.server.handlers;

import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataAction;
import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataResult;
import com.sokolenko.jcrconsole.shared.protocol.NodeData;
import com.sokolenko.jcrconsole.shared.protocol.PropertyData;
import com.sokolenko.jcrconsole.shared.protocol.PropertyTypeData;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.jcr.PropertyType.*;

/**
 * @author Anatoliy Sokolenko
 */
@Component
public class GetNodeDataActionHandler extends AbstractJcrActionHandler<GetNodeDataAction, GetNodeDataResult> {
    protected static final Map<Integer, PropertyTypeData> PROPERTY_TYPES = new HashMap<Integer, PropertyTypeData>();

    static {
        PROPERTY_TYPES.put( STRING, PropertyTypeData.STRING );
        PROPERTY_TYPES.put( BINARY, PropertyTypeData.BINARY );
        PROPERTY_TYPES.put( LONG, PropertyTypeData.LONG );
        PROPERTY_TYPES.put( DOUBLE, PropertyTypeData.DOUBLE );
        PROPERTY_TYPES.put( DATE, PropertyTypeData.DATE );
        PROPERTY_TYPES.put( BOOLEAN, PropertyTypeData.BOOLEAN );
        PROPERTY_TYPES.put( NAME, PropertyTypeData.NAME );
        PROPERTY_TYPES.put( PATH, PropertyTypeData.PATH );
        PROPERTY_TYPES.put( REFERENCE, PropertyTypeData.REFERENCE );
        PROPERTY_TYPES.put( WEAKREFERENCE, PropertyTypeData.WEAK_REFERENCE );
        PROPERTY_TYPES.put( URI, PropertyTypeData.URI );
        PROPERTY_TYPES.put( DECIMAL, PropertyTypeData.DECIMAL );
        PROPERTY_TYPES.put( UNDEFINED, PropertyTypeData.UNDEFINED );
    }

    public GetNodeDataActionHandler() {
        super( GetNodeDataAction.class );
    }

    @Override
    public GetNodeDataResult execute( GetNodeDataAction action, ExecutionContext context ) throws DispatchException {
        try {
            NodeData nodeData = new NodeData();

            Node node = getSession().getNode( action.getNodePath() );
            nodeData.setPath( node.getPath() );
            String primaryNodeTypeName = node.getPrimaryNodeType().getName();

            nodeData.getNodeTypeNames().add( primaryNodeTypeName );

            for ( NodeType mixin : node.getMixinNodeTypes() ) {
                nodeData.getNodeTypeNames().add( mixin.getName() );
            }

            for ( PropertyIterator propertyIt = node.getProperties(); propertyIt.hasNext(); ) {
                Property property = propertyIt.nextProperty();
                PropertyData propertyData = buildPropertyData( property );

                nodeData.getPropertyDatas().add( propertyData );
            }

            return new GetNodeDataResult( nodeData );
        } catch ( RepositoryException e ) {
            throw new RuntimeException( e );
            //TODO
        }
    }

    @Override
    public void rollback( GetNodeDataAction action, GetNodeDataResult result, ExecutionContext context ) throws DispatchException {

    }

    protected PropertyData buildPropertyData( Property property ) throws RepositoryException {
        PropertyData result = new PropertyData();
        result.setName( property.getName() );
        result.setMultiple( property.isMultiple() );

        List<String> stringValues = new ArrayList<String>();
        if ( property.isMultiple() ) {
            Value[] values = property.getValues();
            for ( Value value : values ) {
                stringValues.add( value.getString() );
            }
        } else {
            stringValues.add( property.getString() );
        }

        result.setValues( stringValues );

        PropertyTypeData propertyType = PROPERTY_TYPES.get( property.getType() );
        result.setPropertyTypeData( propertyType );

        return result;
    }
}
