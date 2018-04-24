package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;

public interface BackupListener {

	void onFilesCount(long numbers);

	void onFinish();

	void onFileProcessed(File curFile, File destFile);

	void onFileProcessError(File curFile, IOException e);
}
