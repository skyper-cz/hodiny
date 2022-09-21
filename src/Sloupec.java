import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Sloupec extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Map<TextAttribute, Object> eksuelySeToDocelaDa = new HashMap<>();
        eksuelySeToDocelaDa.put(TextAttribute.FAMILY, Font.DIALOG);
        eksuelySeToDocelaDa.put(TextAttribute.SIZE, 45);
        if (column == 0) {
            eksuelySeToDocelaDa.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_HEAVY);
            l.setFont(Font.getFont(eksuelySeToDocelaDa));
        } else {
            eksuelySeToDocelaDa.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
            l.setFont(Font.getFont(eksuelySeToDocelaDa));
        }
        return l;
    }
}