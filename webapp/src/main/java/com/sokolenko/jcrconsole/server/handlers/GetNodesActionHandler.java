package com.sokolenko.jcrconsole.server.handlers;

import com.sokolenko.jcrconsole.shared.protocol.GetNodesAction;
import com.sokolenko.jcrconsole.shared.protocol.GetNodesResult;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
@Component
public class GetNodesActionHandler extends JcrActionHandler<GetNodesAction, GetNodesResult> {
    public GetNodesActionHandler() {
        super( GetNodesAction.class );
    }

    @Override
    public GetNodesResult execute( GetNodesAction action, ExecutionContext context ) throws DispatchException {
        try {
            List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

            String nodePath = action.getPath();

            Node node = getSession().getNode( nodePath );
            if ( action.isSelfNode() ) {
                NodeInfo nodeInfo = convert( node );

                nodeInfos.add( nodeInfo );
            } else {
                NodeIterator childrenNodeIterator = node.getNodes();
                childrenNodeIterator.skip( action.getBucketStart() );

                for ( int nodeId = 0; nodeId < action.getBucketLength() && childrenNodeIterator.hasNext(); nodeId++ ) {
                    NodeInfo nodeInfo = convert( childrenNodeIterator.nextNode() );

                    nodeInfos.add( nodeInfo );
                }
            }

            GetNodesResult result = new GetNodesResult();
            result.setNodeInfos( nodeInfos );

            return result;
        } catch ( RepositoryException e ) {
            throw new ActionException( e );
        }
    }

    @Override
    public void rollback( GetNodesAction action, GetNodesResult result, ExecutionContext context ) throws DispatchException {

    }

    protected NodeInfo convert( Node node ) throws RepositoryException {
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeName( node.getName() );
        nodeInfo.setNodePath( node.getPath() );
        nodeInfo.setNodeTypeName( node.getPrimaryNodeType().getName() );
        nodeInfo.setTotalChildrenCount( node.getNodes().getSize() );

        return nodeInfo;
    }
}
