package jo.vpl.block.io;

import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import java.io.File;
import javafx.scene.control.Label;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "IO.ReadIfc",
        category = "io",
        description = "Read an Ifc file",
        tags = {"io","view", "3D"})
public class ReadIfc extends Block {

    /**
     * A hub that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public ReadIfc(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Read .ifc");

        addInPortToHub("file", File.class);
        addOutPortToHub("file", File.class);

        Label label = new Label(".ifc");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);
    }

    @Override
    public void calculate() {
        //Get controls and data

        //Do action
        //Set data
    }

    @Override
    public Block clone() {
        Block hub = new ReadIfc(hostCanvas);
        return hub;
    }

}
