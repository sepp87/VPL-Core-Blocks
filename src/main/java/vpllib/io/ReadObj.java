package vpllib.io;

import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import vpllib.watch3D.ObjParser;
import java.io.File;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Label;
import vplcore.IconType;
import vplcore.graph.model.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "IO.ReadObj",
        category = "io",
        description = "Read a Wavefront .obj file",
        tags = {"read", "obj", "parse"}
)
public class ReadObj extends Block {

    /**
     * A block that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public ReadObj(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Obj");

        addInPortToBlock("file", File.class);
        addOutPortToBlock("group", Group.class);

        Label label = getAwesomeIcon(IconType.FA_COGS);

        addControlToBlock(label);
    }

    @Override
    public void calculate() {
        //Get controls and data
        File file = (File) inPorts.get(0).getData();
        List<Group> groups = null;


        //Do action
        if (file != null && file.exists() && file.isFile() && file.getPath().endsWith(".obj")) {
            groups = ObjParser.parseObj(file);
        }

        //Set data
        outPorts.get(0).setData(groups);
    }

    @Override
    public Block clone() {
        Block block = new ReadObj(workspace);
        return block;
    }

}
