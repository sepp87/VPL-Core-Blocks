package jo.vpl.block.math;

import java.util.List;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import jo.vpl.util.IconType;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Math.Subtract",
        category = "Math",
        description = "Subtract B from A",
        tags = {"math", "subtract", "min"})
public class Subtract extends Block {

    public Subtract(Workspace hostCanvas) {
        super(hostCanvas);

        setName("A-B");

        addInPortToBlock("double : A", double.class);
        addInPortToBlock("double : B", double.class);

        addOutPortToBlock("double : Result", double.class);

        Label label = getAwesomeIcon(IconType.FA_MINUS);

        addControlToBlock(label);
    }

    /**
     * Calculate X-Y
     */
    @Override
    public void calculate() {

        //Get data
        Object raw1 = inPorts.get(0).getData();
        Object raw2 = inPorts.get(1).getData();

        //Finish calculate if there is no incoming data
        if (raw1 == null || raw2 == null) {
            outPorts.get(0).setData(null);
            return;
        }

        if (raw1 instanceof List || raw2 instanceof List) {
            //not yet supported
            outPorts.get(0).setData(null);
        } else {
            double result = (double) raw1 / (double) raw2;
            outPorts.get(0).setData(result);
        }

    }

    @Override
    public Block clone() {
        Block hub = new Subtract(hostCanvas);
        return hub;
    }
}
