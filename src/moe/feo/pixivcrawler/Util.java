package moe.feo.pixivcrawler;

import java.io.File;
import java.io.IOException;

public class Util {

	public static File createFolder(String folderPath) {// 创建文件夹
		File folder = new File(folderPath);
		folder.mkdirs();
		return folder;
	}

	public static File createFile(String parent, String fileName) {// 创建文件
		createFolder(parent);
		File folder = new File(parent);
		File file = new File(folder, fileName);
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void createFile(File file) {
		if (!file.getParentFile().exists()) { // 如果父文件夹不存在
			file.getParentFile().mkdirs(); // 创建父文件夹
		}
		if (file.exists()) { // 如果文件已经存在
			file.delete(); // 删除此文件
		}
	}
}
