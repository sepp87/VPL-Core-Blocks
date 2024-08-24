package jo.vpl.block.math;

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
        identifier = "Math.Multiply",
        category = "Math",
        description = "Multiply A by B",
        tags = {"math", "multiply"})
public class Multiply extends Block {

    public Multiply(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Mul");

        addInPortToHub("double : A", double.class);
        addInPortToHub("double : B", double.class);

        addOutPortToHub("double : Result", double.class);

        Label label = new Label("X");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);
    }

    /**
     * Calculate X*Y
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
            double result = (double) raw1 * (double) raw2;
            outPorts.get(0).setData(result);

        }
    }

    @Override
    public Block clone() {
        Block hub = new Multiply(hostCanvas);
        return hub;
    }
}
