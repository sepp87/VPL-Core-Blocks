package jo.vpl.block.list;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import javax.xml.namespace.QName;
import jo.vpl.core.Port;
import jo.vpl.xml.HubTag;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "List.ClearList",
        category = "List",
        description = "Clear the incoming list",
        tags = {"list", "clear", "empty"})
public class ClearList extends Block {

    public ClearList(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Clear");

        //There is no checking of list in port make connection boolean statement
        //Might want to fix that!
        addInPortToHub("Object : List", Object.class);

        Label label = new Label("Clear");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);

        List list = jo.vpl.core.Util.getList(Object.class);
        outPorts.get(0).setData(list);
    }

    /**
     * calculate function is called whenever new data is incoming
     */
    @Override
    public void calculate() {

        Object raw = inPorts.get(0).getData();
        if (raw == null) {
            return;
        }

        if (raw instanceof List) {
            List list = (List) raw;
            list.clear();
        }
    }

    @Override
    public void serialize(HubTag xmlTag) {
        super.serialize(xmlTag);
        //Retrieval of custom attribute
    }

    @Override
    public void deserialize(HubTag xmlTag) {
        super.deserialize(xmlTag);
        //Retrieval of custom attribute
    }

    @Override
    public Block clone() {
        ClearList hub = new ClearList(hostCanvas);
        //Specify further copy statements here
        return hub;
    }
}
