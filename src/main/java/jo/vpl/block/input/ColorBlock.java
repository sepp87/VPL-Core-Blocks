package jo.vpl.block.input;

import jo.vpl.core.Block;
import jo.vpl.core.Workspace;
import javafx.scene.paint.Color;
import javax.xml.namespace.QName;
import jo.vpl.util.CustomColorBox;
import jo.vpl.xml.BlockTag;
import jo.vpl.core.BlockInfo;

/**
 *
 * @author JoostMeulenkamp
 */
@BlockInfo(
        identifier = "In.Color",
        category = "Input",
        description = "Pick a nice color from the palette",
        tags = {"input", "color"})
public class ColorBlock extends Block {

    public ColorBlock(Workspace hostCanvas) {
        super(hostCanvas);

        setName("Color Picker");

        addOutPortToBlock("color", Color.class);

        CustomColorBox picker = new CustomColorBox();

        outPorts.get(0).dataProperty().bind(picker.customColorProperty());

        addControlToBlock(picker);
    }

    public Color getColor() {
        CustomColorBox picker = (CustomColorBox) controls.get(0);
        return picker.customColorProperty().get();
    }

    public void setColor(Color color) {
        CustomColorBox picker = (CustomColorBox) controls.get(0);
        picker.customColorProperty().set(color);
    }

    @Override
    public void calculate() {
    }

    @Override
    public void serialize(BlockTag xmlTag) {
        super.serialize(xmlTag);
        xmlTag.getOtherAttributes().put(QName.valueOf("color"), getColor().toString());
    }

    @Override
    public void deserialize(BlockTag xmlTag) {
        super.deserialize(xmlTag);
        String color = xmlTag.getOtherAttributes().get(QName.valueOf("color"));
        this.setColor(Color.valueOf(color));
    }

    @Override
    public Block clone() {
        ColorBlock hub = new ColorBlock(hostCanvas);
        hub.setColor(this.getColor());
        return hub;
    }
}
