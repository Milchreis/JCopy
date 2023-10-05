package de.milchreis.jcopy;

public interface ViewListener {

    void updateDisable(boolean isDisable);

    void updateProgress(long currentNumber, long numberOfFiles);
}
