package de.milchreis.jcopy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class Tools {

	public static List<File> readIn(File session) throws IOException {
		return Files.readAllLines(session.toPath()).stream()
				.map(line -> new File(line.trim()))
				.collect(Collectors.toList());
	}

	public static void writeOut(File session, List<File> save) throws IOException {
		String content = save.stream()
				.map(f -> f.getAbsolutePath())
				.collect(Collectors.joining("\n"));
		
		Files.write(session.toPath(), content.getBytes());
	}

	public static long getFileCount(List<File> sources) throws IOException {
		long counter = 0;
		
		for(File source : sources) {
			counter += Files.walk(source.toPath())
				.parallel()
				.filter(p -> !p.toFile().isDirectory())
				.count();
		}
		
		return counter;
	}

	public static void backupFiles(File f, File destination, BackupListener updateCallback) throws IOException {

		Files.walk(f.toPath()).parallel().forEach(p -> {
		
			File curFile = p.toFile();
			System.out.println(curFile);

			if(curFile.isFile()) {
				String subpath = curFile.getAbsolutePath().replace(f.getParentFile().getAbsolutePath(), "");
				File destFile = new File(destination, subpath);
				
				try {
					if(destFile.exists()) {
						if(curFile.lastModified() -  destFile.lastModified() > 1) {
							FileUtils.copyFile(curFile, destFile);
							System.out.println("Copy: " + destFile.getAbsolutePath());
						}
					} else {
						destFile.getParentFile().mkdirs();
						FileUtils.copyFile(curFile, destFile);
						System.out.println("First copy: " + destFile.getAbsolutePath());
					}
					
					if(updateCallback != null) {
						updateCallback.onFileProcessed(curFile, destFile);
					}
				} catch (IOException e) {
					
					if(updateCallback != null) {
						updateCallback.onFileProcessError(curFile, e);
					}
				}
			}
		});
	}

}
