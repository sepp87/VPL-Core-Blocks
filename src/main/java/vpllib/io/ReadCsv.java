package vpllib.io;

import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import java.io.File;
import java.util.List;
import javafx.scene.control.Label;
import vplcore.graph.model.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "IO.ReadCsv",
        category = "io",
        description = "Read a CSV file",
        tags = {"io", "csv", "read", "parse"})
public class ReadCsv extends Block {

    /**
     * A block that reads a CSV file and outputs it as multiple lists of strings
     *
     * @param hostCanvas
     */
    public ReadCsv(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Read .csv");

        addInPortToBlock("file", File.class);
        addOutPortToBlock("list", List.class);

        Label label = new Label(".csv");
        label.getStyleClass().add("block-text");

        addControlToBlock(label);
    }

    @Override
    public void calculate() {
        //Get controls and data
        File file = (File) inPorts.get(0).getData();
        List<List<String>> lists = null;

        //Do action
        if (file != null && file.exists() && file.isFile() && file.getPath().endsWith(".csv")) {
            lists = vplcore.Util.readCommaSeperatedFile(true, file);
        }

        //Set data
        outPorts.get(0).setData(lists);

    }

    @Override
    public Block clone() {
        Block block = new ReadCsv(workspace);
        return block;
    }

}
