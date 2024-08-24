package jo.vpl.block;

import jo.vpl.block.TemplateHub;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import javax.xml.namespace.QName;
import jo.vpl.core.Port;
import jo.vpl.util.IconType;
import jo.vpl.xml.HubTag;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Util.TestHub",
        category = "General",
        description = "A template hub for further customization",
        tags = {"template", "dummy", "example"})
public class TestHub extends Block {

    public TestHub(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Template");

        addInPortToHub("Object", Object.class);

        addOutPortToHub("String", String.class);

        Label label = getAwesomeIcon(IconType.FA_PAPER_PLANE);
        addControlToHub(label);
    }

    /**
     * Function to handle data when a connection is added and before calculate
     * is called
     */
    @Override
    public void handle_IncomingConnectionAdded(Port source, Port incoming) {
        //Sample code for handling just specific ports
        int index = inPorts.indexOf(source);
        if (index == 0) {

        }
    }

    /**
     * Function to handle data when a connection is removed
     */
    @Override
    public void handle_IncomingConnectionRemoved(Port source) {
        //Sample code for handling just specific ports
        int index = inPorts.indexOf(source);
        if (index == 0) {

        }
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
            List<Object> nodes = (List<Object>) raw;

            //Example code to handle collections
            List<String> strList = nodes.stream()
                    .map(e -> e.toString())
                    .collect(Collectors.toCollection(ArrayList<String>::new));

            //Set outgoing data
            outPorts.get(0).setData(strList);

        } else {
            //Example code to handle a single object instance
            String str = ((Object) raw).toString();

            //Set outgoing data
            outPorts.get(0).setData(str);
        }
    }

    @Override
    public void serialize(HubTag xmlTag) {
        super.serialize(xmlTag);
        //Retrieval of custom attribute
        xmlTag.getOtherAttributes().put(QName.valueOf("key"), "value");
    }

    @Override
    public void deserialize(HubTag xmlTag) {
        super.deserialize(xmlTag);
        //Retrieval of custom attribute
        String value = xmlTag.getOtherAttributes().get(QName.valueOf("key"));
        //Specify further initialization statements here
        this.calculate();
    }

    @Override
    public Block clone() {
        TemplateHub hub = new TemplateHub(hostCanvas);
        //Specify further copy statements here
        return hub;
    }
}
