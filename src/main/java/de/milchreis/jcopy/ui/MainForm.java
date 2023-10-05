package de.milchreis.jcopy.ui;

import de.milchreis.jcopy.AppModel;
import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainForm {

    private final AppModel model;

    public MainForm(AppModel model) {
        this.model = model;
    }

    public void show() {

        final UiBooster booster = new UiBooster(UiBoosterOptions.Theme.DARK_THEME, "icon.png");

        final ResourceBundle bundle = getSuitableBundle();

        final ViewController controller = new ViewController(model);

        final Form form = booster.createForm(bundle.getString("window.title"))
                .addCustomElement(new LogoElement())
                // @formatter:off
                .startRow()
                    .addList("", getSourcesAsListElements())
                    .addCustomElement(new DirectoryButtonsElement(bundle, controller))
                .endRow()
                // @formatter:on
                .addButton(calcDestinationLabel(bundle), controller::onSelectDestination).setID("destination")
                .addButton(bundle.getString("start"), () -> controller.onStartBackup())
                .addProgress("", 0, 100, 0).setID("progress").setDisabled()
                .andWindow().setSize(600, 500).save()
                .setCloseListener(l -> System.exit(0))
                .setChangeListener((formElement, o, form1) -> form1.getById("destination").setValue(calcDestinationLabel(bundle)))
                .run();

        controller.setList(form.getByIndex(1).toList());
        controller.setProgress(form.getById("progress").toProgress());
        controller.setDestinationButton(form.getById("destination").toButton());
    }

    private String calcDestinationLabel(ResourceBundle bundle) {
        return model.getDestination() == null
                ? bundle.getString("destination.select")
                : bundle.getString("destination") + ": " + model.getDestination().getAbsolutePath();
    }

    private ListElement[] getSourcesAsListElements() {
        return model.getSources().stream()
                .map(s -> new ListElement(s.getAbsolutePath(), null))
                .toArray(ListElement[]::new);
    }

    private ResourceBundle getSuitableBundle() {
        try {
            return ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
        } catch (Exception e) {
            return ResourceBundle.getBundle("MessagesBundle", new Locale("en_US"));
        }
    }
}
