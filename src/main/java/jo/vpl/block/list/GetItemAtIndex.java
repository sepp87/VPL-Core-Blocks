package jo.vpl.block.list;

import java.util.ArrayList;
import java.util.List;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import jo.vpl.core.Port;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "List.GetItemAtIndex",
        category = "List",
        description = "Get an item from a list by its index",
        tags = {"list", "index", "item"})
public class GetItemAtIndex extends Block {

    public GetItemAtIndex(Workspace hostCanvas) {
        super(hostCanvas);

        setName("i");

        //There is no checking of list in port make connection boolean statement
        //Might want to fix that!
        addInPortToBlock("List", Object.class);
        addInPortToBlock("int", int.class);

        addOutPortToBlock("Object", Object.class);

        Label label = new Label("Nth");
        label.getStyleClass().add("hub-text");

        addControlToBlock(label);
    }

    @Override
    public void handle_IncomingConnectionAdded(Port source, Port incoming) {
        int index = inPorts.indexOf(source);
        if (index == 0) {
            //Set data type corresponding to incoming
            outPorts.get(0).dataType = incoming.dataType;
            outPorts.get(0).setName(incoming.getName());
        }
    }

    @Override
    public void handle_IncomingConnectionRemoved(Port source) {
        int index = inPorts.indexOf(source);
        if (index == 0) {
            //Reset data type to initial state
            outPorts.get(0).dataType = Object.class;
            outPorts.get(0).setName("Object");
        }
    }

    /**
     * calculate function is called whenever new data is incoming
     */
    @Override
    public void calculate() {

        //Get incoming data
        Object raw = inPorts.get(0).getData();
        Object index = inPorts.get(1).getData();

        //Finish calculate if there is no incoming data
        if (raw == null) {
            outPorts.get(0).setData(null);
            return;
        }

        //Process incoming data
        if (raw instanceof List) {

            List source = (List) raw;

            //Example code to handle collections
            Object out = null;
            if (index == null) {
                out = source;
            } else if (index instanceof List) {
                List<Integer> indeces = (List<Integer>) index;
                for (int i : indeces) {
                    List target = new ArrayList<>();
                    target.add(source.get(i));
                    out = target;
                }
            } else {
                out = source.get((Integer) index);
            }

            //Set outgoing data
            outPorts.get(0).setData(out);
        }
    }

    @Override
    public Block clone() {
        GetItemAtIndex hub = new GetItemAtIndex(hostCanvas);
        //Specify further copy statements here
        return hub;
    }
}
