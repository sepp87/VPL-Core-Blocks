package jo.vpl.hub.io;

import jo.vpl.core.Hub;
import jo.vpl.core.Workspace;
import java.io.File;
import java.util.List;
import javafx.scene.control.Label;
import jo.vpl.core.HubInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@HubInfo(
        identifier = "IO.ReadCsv",
        category = "io",
        description = "Read a CSV file",
        tags = {"io", "csv", "read", "parse"})
public class ReadCsv extends Hub {

    /**
     * A hub that reads a CSV file and outputs it as multiple lists of strings
     *
     * @param hostCanvas
     */
    public ReadCsv(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Read .csv");

        addInPortToHub("file", File.class);
        addOutPortToHub("list", List.class);

        Label label = new Label(".csv");
        label.getStyleClass().add("hub-text");

        addControlToHub(label);
    }

    @Override
    public void calculate() {
        //Get controls and data
        File file = (File) inPorts.get(0).getData();
        List<List<String>> lists = null;

        //Do action
        if (file != null && file.exists() && file.isFile() && file.getPath().endsWith(".csv")) {
            lists = jo.vpl.core.Util.readCommaSeperatedFile(true, file);
        }

        //Set data
        outPorts.get(0).setData(lists);

    }

    @Override
    public Hub clone() {
        Hub hub = new ReadCsv(hostCanvas);
        return hub;
    }

}
