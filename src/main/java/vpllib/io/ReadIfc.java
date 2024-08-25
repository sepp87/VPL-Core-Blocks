package vpllib.io;

import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import java.io.File;
import javafx.scene.control.Label;
import vplcore.graph.model.BlockInfo;

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
     * A block that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public ReadIfc(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Read .ifc");

        addInPortToBlock("file", File.class);
        addOutPortToBlock("file", File.class);

        Label label = new Label(".ifc");
        label.getStyleClass().add("block-text");

        addControlToBlock(label);
    }

    @Override
    public void calculate() {
        //Get controls and data

        //Do action
        //Set data
    }

    @Override
    public Block clone() {
        Block block = new ReadIfc(workspace);
        return block;
    }

}
