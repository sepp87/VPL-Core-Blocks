package jo.vpl.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.Node;
import javafx.scene.control.Label;
import jo.vpl.util.IconType;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Util.GetId",
        category = "General",
        description = "Get the Id of this object.",
        tags = {"util", "id", "general"})
public class GetId extends Block {

    public GetId(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Id");

        addInPortToBlock("node", Node.class);

        addOutPortToBlock("str", String.class);

        Label label = getAwesomeIcon(IconType.FA_BARCODE);
        addControlToBlock(label);
    }

    /**
     * Get the Id's
     */
    @Override
    public void calculate() {

        //Get controls and data
        Object raw = inPorts.get(0).getData();

        if (raw == null) {
            return;
        }

        //Process geomData
        if (raw instanceof List) {
            List list = (List) raw;
            List<Node> nodes = (List<Node>) list;

            List<String> ids = nodes.stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toCollection(ArrayList<String>::new));

            outPorts.get(0).setData(ids);

        } else {
            String id = ((Node) raw).getId();
            outPorts.get(0).setData(id);
        }

    }

    @Override
    public Block clone() {
        Block hub = new GetId(hostCanvas);
        return hub;
    }
}
