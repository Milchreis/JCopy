package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppModel {

	private List<File> sources = new ArrayList<>();
	private File destination;
	private File session = new File(System.getProperty("user.home"), ".jcopy");
	
	private long currentNumber;
	private long numberOfFiles;
	
	private BackupListener updateCallback;
	
	public AppModel(File session) throws IOException {
		this.session = session;
		init();
	}
	
	public AppModel() throws IOException {
		init();
	}
	
	public void init() throws IOException {
		if(session.exists()) {
			List<File> files = Tools.readIn(session);
			
			if(files.size() >= 2) {
				destination = files.get(0);
				files.remove(0);
				
				sources = files; 
			}
		}
	}
	
	public void addSource(File source) {
		File foundsource = sources.stream()
				.filter(f -> f.getAbsolutePath().equals(source.getAbsolutePath()))
				.findAny()
				.orElse(null);
		
		if(foundsource == null) {
			sources.add(source);
		}
	}
	
	public void removeSource(File source) {
		sources = sources.stream()
				.filter(f -> !f.getAbsolutePath().equals(source.getAbsolutePath()))
				.collect(Collectors.toList());
	}
	
	public void backup(BackupListener _updateCallback) throws IOException {
		
		if(session != null) {
			List<File> save = new ArrayList<>();
			save.add(destination);
			save.addAll(sources);
			Tools.writeOut(session, save);
		}
		
		if(_updateCallback != null) {
			updateCallback = _updateCallback;
			
			long numbers = Tools.getFileCount(sources);
			updateCallback.onFilesCount(numbers);
			
			sources.forEach(f -> {
				try {
					Tools.backupFiles(f, destination, updateCallback);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			updateCallback.onFinish();
		}
	}

	public File getDestination() {
		return destination;
	}

	public void setDestination(File destination) {
		this.destination = destination;
	}

	public List<File> getSources() {
		return sources;
	}

	public long getNumberOfFiles() {
		return numberOfFiles;
	}

	public void setNumberOfFiles(long numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}

	public long getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(long currentNumber) {
		this.currentNumber = currentNumber;
	}
}
