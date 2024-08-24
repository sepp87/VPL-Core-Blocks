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
        identifier = "IO.ConvertIfcToCsv",
        category = "io",
        description = "Convert Ifc to Obj",
        tags = {"io", "ifc", "obj", "convert", "read", "parse"})
public class ConvertIfcToObj extends Block {

    /**
     * A hub that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public ConvertIfcToObj(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Ifc to Obj");

        addInPortToBlock("file", File.class);
        addOutPortToBlock("file", File.class);

        Label label = new Label("Ifc>Obj");
        label.getStyleClass().add("hub-text");

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
        Block hub = new ConvertIfcToObj(hostCanvas);
        return hub;
    }

}
