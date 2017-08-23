package panels;

import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GridBagHelper extends GridBagLayout {
    private GridBagConstraints _constraints;
    private JComponent _component;

    public GridBagHelper(JComponent component) {
        super();
        _component = component;
        _constraints = new GridBagConstraints();
        component.setLayout(this);
    }

    public GridBagConstraints getConstraints() {
        return _constraints;
    }


    public void changeConstraints(int anchor, int weightX, int weightY, int row, int column, Insets insets) {
        _constraints = new GridBagConstraints();
        _constraints.anchor = anchor;
        _constraints.weightx = weightX;
        _constraints.weighty = weightY;
        _constraints.gridx = row;
        _constraints.gridy = column;
        _constraints.insets = insets;
    }


    public JButton createButton(String title, int row, int column, Insets insets, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(200,60));
        button.setBackground(Color.lightGray);
        button.setFont(FileUtils.getDefaultFont(Font.BOLD, 20));
        button.addActionListener(actionListener);

        changeConstraints(GridBagConstraints.CENTER, 10, 1, row, column, insets);

        _component.add(button, _constraints);

        return button;
    }


    public void createImage(Image image, int row, int column, int weightX, Insets insets, int width, int height, int gridWidth) {
        JLabel picLabel = new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        picLabel.setOpaque(false);

        changeConstraints(GridBagConstraints.WEST, weightX, 1, row, column, insets);
        _constraints.gridwidth = gridWidth;

        _component.add(picLabel, _constraints);
    }


    public JLabel createLabel(String text, int row, int column, int weightX, Insets insets, int gridWidth, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Calibri", Font.BOLD, fontSize));

        changeConstraints(GridBagConstraints.WEST, weightX, 1, row, column, insets);
        _constraints.gridwidth = gridWidth;
        _component.add(label, _constraints);

        return label;
    }
}
