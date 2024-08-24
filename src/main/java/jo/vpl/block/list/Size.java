package jo.vpl.block.list;

import java.util.List;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "List.Size",
        category = "List",
        description = "Get the list length",
        tags = {"list", "size", "length"})
public class Size extends Block {

    public Size(Workspace hostCanvas) {
        super(hostCanvas);

        setName("l");

        //There is no checking of list in port make connection boolean statement
        //Might want to fix that!
        addInPortToBlock("list", Object.class);

        addOutPortToBlock("size", int.class);

        Label label = new Label("Size");
        label.getStyleClass().add("hub-text");
        
        addControlToBlock(label);
    }

    /**
     * calculate function is called whenever new data is incoming
     */
    @Override
    public void calculate() {

        //Get incoming data
        Object raw = inPorts.get(0).getData();

        //Finish calculate if there is no incoming data
        if (raw == null) {
            outPorts.get(0).setData(null);
            return;
        }

        //Process incoming data
        if (raw instanceof List) {

            List source = (List) raw;

            //Set outgoing data
            outPorts.get(0).setData(source.size());
        }
    }

    @Override
    public Block clone() {
        Size hub = new Size(hostCanvas);
        //Specify further copy statements here
        return hub;
    }
}
