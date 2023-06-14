package com.yaac.view.UIComponent;

import javax.swing.*;
import java.awt.*;

public class Renderer extends DefaultListCellRenderer {
    public Renderer() {
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean hasFocus) {
        JLabel l = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, hasFocus);
        if (isSelected) {
            l.setForeground(Color.WHITE);
            l.setBackground(new Color(0,0,0,150));
        }
        return l;
    }
}
