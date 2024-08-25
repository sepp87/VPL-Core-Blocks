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
        identifier = "IO.ConvertIfcToCsv",
        category = "io",
        description = "Convert Ifc to Obj",
        tags = {"io", "ifc", "obj", "convert", "read", "parse"})
public class ConvertIfcToObj extends Block {

    /**
     * A block that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public ConvertIfcToObj(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Ifc to Obj");

        addInPortToBlock("file", File.class);
        addOutPortToBlock("file", File.class);

        Label label = new Label("Ifc>Obj");
        label.getStyleClass().add("block-text");

        addControlToBlock(label);
    }

    @Override
    public void calculate() {
        //Get controls and data
        File file = (File) inPorts.get(0).getData();
        File obj = null;

        //Do action
        if (file != null && file.exists() && file.isFile() && file.getPath().endsWith(".ifc")) {

        }

        //Set data
        outPorts.get(0).setData(obj);

    }

    @Override
    public Block clone() {
        Block block = new ConvertIfcToObj(workspace);
        return block;
    }

}
