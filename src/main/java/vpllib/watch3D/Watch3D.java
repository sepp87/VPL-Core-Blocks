package vpllib.watch3D;

import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import vpllib.watch3D.ObjViewer;
import java.util.List;
import javafx.scene.Group;
import vplcore.graph.model.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Util.Watch3D",
        category = "View",
        description = "View 3D geometry",
        tags = {"view", "3D"})
public class Watch3D extends Block {

    /**
     * A block that embeds an external Obj viewer class
     *
     * @param hostCanvas
     */
    public Watch3D(Workspace hostCanvas) {
        super(hostCanvas);
        setResizable(true);

        setName("Watch3D");

        mainContentGrid.setPrefWidth(320);
        mainContentGrid.setPrefHeight(240);
        addInPortToBlock("group", Group.class);
        addOutPortToBlock("group", Group.class);

        ObjViewer viewer = new ObjViewer(mainContentGrid);

        addControlToBlock(viewer);
    }

    @Override
    public void calculate() {
        //Get controls and data
        ObjViewer viewer = (ObjViewer) controls.get(0);
        Object geomData = inPorts.get(0).getData();
        List<Group> groups = null;

        //Process geomData
        if (geomData != null && geomData instanceof List) {
            List list = (List) geomData;
            if (list.get(0) instanceof Group) {
                groups = (List<Group>) list;
            }
        }

        //Do action add geometry
        viewer.clearGeometry();
        if (groups != null) {
            for (Group group : groups) {
                viewer.addGeometry(group);
            }
        }

        //Set data
        outPorts.get(0).setData(groups);
    }

    @Override
    public Block clone() {
        Block block = new Watch3D(workspace);
        return block;
    }

}
