package vpllib.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javax.xml.namespace.QName;
import vplcore.graph.model.Port;
import vplcore.IconType;
import jo.vpl.xml.BlockTag;
import vplcore.graph.model.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Date.ToFormattedString",
        category = "Date",
        description = "Convert a date to another formatted date string",
        tags = {"date", "format", "convert"})
public class ToFormattedString extends Block {
    
    private SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ToFormattedString(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Date");

        addInPortToBlock("String : Date", String.class);
        addOutPortToBlock("String : Date", String.class);

        Label label = getAwesomeIcon(IconType.FA_CLOCK_O);

        addControlToBlock(label);
        
        //Set current date as outgoing data
        String now = targetFormat.format(new Date());
        outPorts.get(0).setData(now);
    }

    @Override
    public void handle_IncomingConnectionRemoved(Port source) {
        //Set current date as outgoing data
        String now = targetFormat.format(new Date());
        outPorts.get(0).setData(now);
    }

    /**
     * calculate function is called whenever new data is incoming
     */
    @Override
    public void calculate() {

        //Get incoming data
        Object raw = inPorts.get(0).getData();

        //Finish calculate if there is no incoming data
        if (raw == null) {
            outPorts.get(0).setData(null);
            return;
        }

        if (raw instanceof List) {
            //This calculate function does not (yet) support lists
        } else {
            //Process incoming data
            String rawDate = (String) raw;
            String pattern = vplcore.Util.getDateFormat(rawDate);

            if (pattern == null) {
                return;
            }

            SimpleDateFormat sourceFormat = new SimpleDateFormat(pattern);

            try {
                Date date = sourceFormat.parse(rawDate);
                String formattedDate = targetFormat.format(date);

                //Set outgoing data
                outPorts.get(0).setData(formattedDate);
            } catch (ParseException ex) {
                Logger.getLogger(ToFormattedString.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @Override
    public void serialize(BlockTag xmlTag) {
        super.serialize(xmlTag);
        //Retrieval of custom attribute
        xmlTag.getOtherAttributes().put(QName.valueOf("key"), "value");
    }

    @Override
    public void deserialize(BlockTag xmlTag) {
        super.deserialize(xmlTag);
        //Retrieval of custom attribute
        String value = xmlTag.getOtherAttributes().get(QName.valueOf("key"));
        //Specify further initialization statements here
        this.calculate();
    }

    @Override
    public Block clone() {
        ToFormattedString block = new ToFormattedString(workspace);
        //Specify further copy statements here
        return block;
    }
}
