package de.milchreis.jcopy.ui;

import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.FormElement;
import de.milchreis.uibooster.model.FormElementChangeListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

public class DirectoryButtonsElement extends FormElement {

    private final ResourceBundle bundle;
    private final ViewController controller;

    public DirectoryButtonsElement(ResourceBundle bundle, ViewController controller) {
        super(null);
        this.bundle = bundle;
        this.controller = controller;
    }

    @Override
    public JComponent createComponent(FormElementChangeListener onChange) {

        final JPanel panel = new JPanel(new GridLayout(5, 1));

        JButton addDirectory = new JButton(bundle.getString("items.add"));
        addDirectory.addActionListener(e -> controller.onAddDirectory());

        JButton removeDirectoryFromList = new JButton(bundle.getString("items.removeItem"));
        removeDirectoryFromList.addActionListener(e -> {
            controller.onRemoveDirectory();
            invokeChangeListener();
        });

        JButton clearList = new JButton(bundle.getString("items.removeAll"));
        clearList.addActionListener(l -> controller.onClearList());

        panel.add(addDirectory);
        panel.add(removeDirectoryFromList);
        panel.add(clearList);

        return panel;
    }

    @Override
    public void setEnabled(boolean enable) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

    }
}
