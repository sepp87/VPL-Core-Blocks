package vpllib.loop;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import vplcore.graph.model.Block;
import vplcore.workspace.Workspace;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.xml.namespace.QName;
import vplcore.graph.model.Port;
import vplcore.IconType;
import jo.vpl.xml.BlockTag;
import vplcore.graph.model.BlockInfo;

/**
 * This class should be analysed, is the switchChangeListener calling from the
 * main JavaFx thread or not? If it is not, this creates unexpected exceptions
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Loop.TimeInterval",
        category = "Loop",
        description = "Loop a block in a given time interval",
        tags = {"loop", "interval", "time", "repeat"})
public class TimeInterval extends Block {

    private final Label TOGGLE;
    private Timer timer = null;
    private Thread timerThread = null;

    public TimeInterval(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Template");

        addInPortToBlock("Object", Object.class);
        addOutPortToBlock("Object", Object.class);

        TOGGLE = getAwesomeIcon(IconType.FA_TOGGLE_OFF);
        addControlToBlock(TOGGLE);

        TOGGLE.setOnMouseClicked(this::button_MouseClick);
    }

    //Toggle between on and off by listening to the switch property of the timer
    private void button_MouseClick(MouseEvent e) {
        if (timer == null) {
            timer = new Timer(300000);
//            timer = new Timer(900000);
//            timer = new Timer(1000);
            timer.switchProperty().addListener(switchChangeListener);
            timerThread = new Thread(timer);
//            reCalculateIncomingBlock();
            timerThread.start();
            IconType type = IconType.FA_TOGGLE_ON;
            TOGGLE.setText(type.getUnicode() + "");
        } else {
            timer.switchProperty().removeListener(switchChangeListener);
            timer.stop();
            timer = null;
            timerThread = null;
            IconType type = IconType.FA_TOGGLE_OFF;
            TOGGLE.setText(type.getUnicode() + "");
        }
    }

    ChangeListener switchChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            Platform.runLater(() -> {
                reCalculateIncomingBlock();
            });
        }
    };

    private void reCalculateIncomingBlock() {
        if (inPorts.get(0).connectedConnections.size() != 1) {
            //throw exception here
            return;
        }
        System.out.println(Thread.currentThread().getName());
        inPorts.get(0).connectedConnections.get(0).getStartPort().parentBlock.calculate();
    }

    @Override
    public void handle_IncomingConnectionAdded(Port source, Port incoming) {
        int index = inPorts.indexOf(source);
        if (index == 0) {
            //Set data type corresponding to incoming
            outPorts.get(0).dataType = incoming.dataType;
            outPorts.get(0).setName(incoming.getName());
        }
    }

    @Override
    public void handle_IncomingConnectionRemoved(Port source) {
        int index = inPorts.indexOf(source);
        if (index == 0) {
            //Reset data type to initial state
            outPorts.get(0).dataType = Object.class;
            outPorts.get(0).setName("Object");
        }
    }

    /**
     * calculate function is called whenever new data is incoming
     */
    @Override
    public void calculate() {
        //Simply forward the data that is coming in

        //Get incoming data
        Object raw = inPorts.get(0).getData();

        //Finish calculate if there is no incoming data
        if (raw == null || timer == null) {
            return;
        }

        //Set outgoing data
        outPorts.get(0).setData(raw);
    }

    private class Timer implements Runnable {

        private final ReadOnlyBooleanWrapper pingPong = new ReadOnlyBooleanWrapper(this, "switch", false);

        private final long START;
        private final long INTERVAL;
        private boolean stop = false;

        private Timer(long interval) {
            START = System.currentTimeMillis();
            INTERVAL = interval;
        }

        @Override
        public void run() {
            while (!stop) {
                long elapsed = START - System.currentTimeMillis();
                int ticks = (int) Math.floor(elapsed / INTERVAL);
                boolean result = ticks % 2 == 0;
                pingPong.set(result);
            }
        }

        public void stop() {
            stop = true;
        }

        public ReadOnlyBooleanProperty switchProperty() {
            return pingPong;
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
        TimeInterval block = new TimeInterval(workspace);
        //Specify further copy statements here
        return block;
    }
}
