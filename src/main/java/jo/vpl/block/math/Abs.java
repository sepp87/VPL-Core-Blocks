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
        identifier = "Math.Abs",
        category = "Math",
        description = "Get the absolute value of A",
        tags = {"math", "abs"})
public class Abs extends Block {

    public Abs(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Abs");

        addInPortToHub("double : A", double.class);

        addOutPortToHub("double : Result", double.class);

        Label label = new Label("|A|");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);
    }

    /**
     * Calculate |X|
     */
    @Override
    public void calculate() {

        //Get data
        Object raw = inPorts.get(0).getData();

        //Finish calculate if there is no incoming data
        if (raw == null) {
            outPorts.get(0).setData(null);
            return;
        }

        if (raw instanceof List) {
            //not yet supported
            outPorts.get(0).setData(null);
        } else {
            double result = Math.abs((double) raw);
            outPorts.get(0).setData(result);
        }

    }

    @Override
    public Block clone() {
        Block hub = new Abs(hostCanvas);
        return hub;
    }
}
