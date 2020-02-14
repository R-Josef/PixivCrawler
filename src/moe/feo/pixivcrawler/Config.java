package moe.feo.pixivcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Config {
	public Map<String, Object> map = new LinkedHashMap<String, Object>();
	private String path; // 这个路径指配置文件名
	private String jarpath;
	private File file;

	public Config(String path) {
		this.path = path;
	}

	public void setJarPath(String path) {
		this.jarpath = path;
	}

	@SuppressWarnings("unchecked")
	public void load(String path) {
		if (file == null) {
			file = new File(jarpath + path);
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			Yaml yaml = new Yaml();
			this.map = (Map<String, Object>) yaml.load(in);
		} catch (FileNotFoundException e1) {
			System.out.print("配置文件未找到.");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.exit(1);
			}
		}
	}

	public void save(String path) {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		Yaml yaml = new Yaml(options);
		Util.createFile(file);
		try {
			FileWriter writer = new FileWriter(file);
			yaml.dump(this.map, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDefaultConfig(String path) {
		if (file == null) {
			file = new File(jarpath + path);
		}
		if (file.exists())
			return;
		// 从jar包内获取配置文件流
		InputStream in = Config.class.getClassLoader().getResourceAsStream(path);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			byte[] bytes = new byte[1024];
			int count = 0;
			while ((count = in.read(bytes)) != -1) {
				out.write(bytes, 0, count);// 将数据写入插件文件
			}
			System.out.println("未找到配置文件，已自动为您生成，请将其配置好后重新运行此程序.");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void Save() {
		this.save(this.path);
	}

	public void Reload() {
		this.load(this.path);
	}

	@SuppressWarnings("unchecked")
	public Object getValue(String path) { // 从指定路径获取类型
		Object value = new Object(); // 初始化返回的值
		if (path.equals(".")) { // 表示根路径
			value = this.map;
		} else if (path.contains(".")) { // 不止一个key
			String[] keys = path.split("\\.");// 分割路径, "."是转义字符必须加\\
			Map<String, Object> map = new LinkedHashMap<String, Object>(); // 此map用于储存遍历得到的map
			map = (Map<String, Object>) this.map.get(keys[0]); // 直接取第一个父键的值放入内部map
			for (int x = 1; x + 1 <= keys.length - 1; x = x + 1) { // 循环从第二个key开始, 至keys的长度-1为止
				map = (Map<String, Object>) map.get(keys[x]);
			}
			value = map.get(keys[keys.length - 1]); // 直接取最后一个key的值作为value
		} else { // 只有一个key, 直接从map获取值
			value = this.map.get(path);
		}
		return value;
	}

	public String getString(String path) { // 从指定路径获取String
		String value = new String();
		value = this.getValue(path).toString();
		return value;
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(String path) { // 从指定路径获取List
		List<String> value = new ArrayList<String>();
		value = (List<String>) this.getValue(path);
		return value;
	}

	@SuppressWarnings("unchecked")
	public List<String> getKey(String path) { // 从指定路径获取所有子健
		List<String> value = new ArrayList<String>();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map = (Map<String, Object>) this.getValue(path);
		for (String key : map.keySet()) {
			value.add(key);
		}
		return value;
	}

	public List<String> getKeyOfValue(String value) { // 通过值查找键
		List<String> keyList = new ArrayList<>();
		for (String key : map.keySet()) {
			String strvalue = String.valueOf(map.get(key)); // 将类型(Integer)转化为String
			if (strvalue.equals(value)) {
				keyList.add(key);
			}
		}
		return keyList;
	}

	public boolean getBoolean(String path) {
		boolean value;
		value = (boolean) this.getValue(path);
		return value;
	}

	@SuppressWarnings("unchecked")
	public void setValue(String path, Object value) {
		if (path.equals(".")) { // 表示根路径
			this.map = (Map<String, Object>) value;
		} else if (path.contains(".")) { // 不止一个key
			String[] keys = path.split("\\.");// 分割路径, "."是转义字符必须加\\
			Map<String, Object> cache = this.map;
			for (int i = 0; i < keys.length - 1; i++) {
				cache = (Map<String, Object>) cache.get(keys[i]);
			}
			cache.put(keys[keys.length - 1], value);
		} else { // 只有一个key, 直接放入值
			map.put(path, value);
		}
	}
}
