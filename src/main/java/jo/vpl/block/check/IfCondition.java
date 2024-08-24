package jo.vpl.block.check;

import java.util.List;
import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.xml.namespace.QName;
import jo.vpl.block.loop.TimeInterval;
import jo.vpl.util.IconType;
import jo.vpl.xml.BlockTag;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "Check.If",
        category = "Check",
        description = "A boolean statement",
        tags = {"check", "if", "condition"})
public class IfCondition extends Block {

    private final Label OPERATOR;
    static String[] operators = {">", ">=", "==", "!=", "<=", "<"};

    public IfCondition(Workspace hostCanvas) {
        super(hostCanvas);

        setName(">");

        addInPortToBlock("Number : A", Number.class);
        addInPortToBlock("Number : B", Number.class);

        addOutPortToBlock("Boolean : Result", Boolean.class);

        OPERATOR = new Label("A > B");
        OPERATOR.setUserData(0);
        OPERATOR.getStyleClass().add("hub-text");
        OPERATOR.setOnMouseClicked(this::button_MouseClick);

        addControlToBlock(OPERATOR);
    }

    //Toggle between on and off by listening to the switch property of the timer
    private void button_MouseClick(MouseEvent e) {

        int index = (int) OPERATOR.getUserData();
        if (index == 5) {
            OPERATOR.setUserData(0);
        } else {
            OPERATOR.setUserData(++index);
        }
        updateOperator();
    }
    
    private void updateOperator() {
        int index = (int) OPERATOR.getUserData();
        OPERATOR.setText("A " + operators[index] + " B");
        calculate();
    }

    /**
     * Calculate X+Y
     */
    @Override
    public void calculate() {

        //Get data
        Object raw1 = inPorts.get(0).getData();
        Object raw2 = inPorts.get(1).getData();

        //Finish calculate if there is no incoming data
        if (raw1 == null || raw2 == null) {
            outPorts.get(0).setData(null);
            return;
        }

        if (raw1 instanceof List || raw2 instanceof List) {
            //not yet supported
            outPorts.get(0).setData(null);

        } else {
            //Does this 
            int index = (int) OPERATOR.getUserData();
            String operator = operators[index];
            Boolean result = null;
            switch (operator) {
                case ">":
                    result = ((Number) raw1).doubleValue() > ((Number) raw2).doubleValue();
                    break;
                case ">=":
                    result = ((Number) raw1).doubleValue() >= ((Number) raw2).doubleValue();
                    break;
                case "==":
                    result = ((Number) raw1).doubleValue() == ((Number) raw2).doubleValue();
                    break;
                case "!=":
                    result = ((Number) raw1).doubleValue() != ((Number) raw2).doubleValue();
                    break;
                case "<=":
                    result = ((Number) raw1).doubleValue() <= ((Number) raw2).doubleValue();
                    break;
                case "<":
                    result = ((Number) raw1).doubleValue() < ((Number) raw2).doubleValue();
                    break;
            }

            outPorts.get(0).setData(result);
        }
    }

    @Override
    public void serialize(BlockTag xmlTag) {
        super.serialize(xmlTag);
        //Retrieval of custom attribute
        xmlTag.getOtherAttributes().put(QName.valueOf("operator"), (int) OPERATOR.getUserData() + "");
    }

    @Override
    public void deserialize(BlockTag xmlTag) {
        super.deserialize(xmlTag);
        //Retrieval of custom attribute
        String value = xmlTag.getOtherAttributes().get(QName.valueOf("operator"));
        int index = Integer.parseInt(value);
        OPERATOR.setUserData(index);
        updateOperator();
    }

    @Override
    public Block clone() {
        IfCondition hub = new IfCondition(hostCanvas);
        hub.OPERATOR.setUserData(this.OPERATOR.getUserData());
        hub.updateOperator();
        return hub;
    }
}
