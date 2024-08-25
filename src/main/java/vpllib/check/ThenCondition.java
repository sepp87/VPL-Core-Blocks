package vpllib.check;

import java.util.List;
import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import javafx.scene.control.Label;
import vplcore.graph.model.Port;
import vplcore.IconType;
import vplcore.graph.model.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Check.Then",
        category = "Check",
        description = "Activate block when condition is true",
        tags = {"check", "if", "condition"})
public class ThenCondition extends Block {

    static String[] operators = {">", ">=", "==", "!=", "<=", "<"};

    public ThenCondition(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Then");

        addInPortToBlock("Boolean : Condition", Boolean.class);
        addInPortToBlock("Object", Object.class);

        addOutPortToBlock("Object", Object.class);

        Label label = new Label("Then");
        label.getStyleClass().add("block-text");

        addControlToBlock(label);
    }

    @Override
    public void handle_IncomingConnectionAdded(Port source, Port incoming) {
        int index = inPorts.indexOf(source);
        if (index == 1) {
            //Set data type corresponding to incoming
            outPorts.get(0).dataType = incoming.dataType;
            outPorts.get(0).setName(incoming.getName());
        }
    }

    @Override
    public void handle_IncomingConnectionRemoved(Port source) {
        int index = inPorts.indexOf(source);
        if (index == 1) {
            //Reset data type to initial state
            outPorts.get(0).dataType = Object.class;
            outPorts.get(0).setName("Object");
        }
    }

    @Override
    public void calculate() {

        //Get data
        Object raw0 = inPorts.get(0).getData();
        Object raw1 = inPorts.get(1).getData();

        //Finish calculate if there is no incoming data
        if (raw0 == null || raw1 == null) {
            outPorts.get(0).setData(null);
            return;
        }

        if (raw0 instanceof List || raw1 instanceof List) {
            //not yet supported
            outPorts.get(0).setData(null);
        } else {
            //Does this 
            boolean isTrue = (Boolean) raw0;
            if (isTrue) {
                outPorts.get(0).setData(raw1);
            } else {
                outPorts.get(0).setData(null);
            }
        }
    }

    @Override
    public Block clone() {
        Block block = new ThenCondition(workspace);
        return block;
    }
}
