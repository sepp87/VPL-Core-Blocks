package jo.vpl.hub.math;

import java.util.List;
import jo.vpl.core.Hub;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import jo.vpl.core.HubInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@HubInfo(
        name = "Math.Divide",
        category = "Math",
        description = "Divide A by B",
        tags = {"math", "divide", "/"})
public class Divide extends Hub {

    public Divide(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Div");

        addInPortToHub("double : A", double.class);
        addInPortToHub("double : B", double.class);

        addOutPortToHub("double : Result", double.class);

        Label label = new Label("/");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);
    }

    /**
     * Calculate X/Y
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
    public Hub clone() {
        Hub hub = new Divide(hostCanvas);
        return hub;
    }
}
