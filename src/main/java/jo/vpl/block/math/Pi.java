package jo.vpl.block.math;

import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
	identifier = "Math.Pi",  
        category = "Math",
        description = "Pi",
	tags = {"math","pi"})
public class Pi extends Block {

    public Pi(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Pi");

        addOutPortToBlock("double : Pi", double.class);        
        
        Label label = new Label("Pi");
        label.getStyleClass().add("hub-text");

        addControlToBlock(label);

        outPorts.get(0).setData(Math.PI);
    }

    /**
     * Output Pi
     */
    @Override
    public void calculate() {
    }

    @Override
    public Block clone() {
        Block hub = new Pi(hostCanvas);
        return hub;
    }
}
