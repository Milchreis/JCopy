package de.milchreis.jcopy.ui;

import de.milchreis.uibooster.components.ImagePanel;
import de.milchreis.uibooster.model.FormElement;
import de.milchreis.uibooster.model.FormElementChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LogoElement extends FormElement {

    public LogoElement() {
        super(null);
    }

    @Override
    public JComponent createComponent(FormElementChangeListener formElementChangeListener) {
        final JPanel panel = new JPanel();
        final BufferedImage image = getBufferedImage("src/main/resources/logo.png");

        final ImagePanel imagePanel = new ImagePanel(image);
        imagePanel.setPreferredSize(new Dimension(image.getWidth()/2, image.getHeight()/2));

        panel.add(imagePanel);
        return panel;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object o) {

    }

    private BufferedImage getBufferedImage(String path) {
        try (InputStream inputStream = new FileInputStream(path)) {
            return ImageIO.read(inputStream);

        } catch (IOException e) {
            throw new IllegalArgumentException("The given file is not ok", e);
        }
    }
}
