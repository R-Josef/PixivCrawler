package moe.feo.pixivcrawler;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class PixivCrawler {

	public static String jarpath = PixivCrawler.class.getClassLoader().getResource("").getPath();
	public static Config config = new Config("config.yml");
	public static SQLiter db = new SQLiter();

	Map<String, String> cookies = new HashMap<String, String>();

	public static void main(String[] args) {

		// 设置配置文件
		config.setJarPath(jarpath);
		config.saveDefaultConfig("config.yml");
		File cfgfile = new File(jarpath + "config.yml");// 给配置文件加锁
		try {
			FileChannel.open(cfgfile.toPath(), StandardOpenOption.READ);
		} catch (IOException e) {
			System.out.println("上锁配置文件失败, 请在接下来的过程中勿编辑配置文件.");
		}
		config.load("config.yml");
		// 设置代理
		String proxyhost = config.getString("proxy.host");
		String proxyport = config.getString("proxy.port");
		if (!proxyhost.isEmpty()) {
			System.setProperty("proxyHost", proxyhost);
		}
		if (!proxyport.isEmpty()) {
			System.setProperty("proxyPort", proxyport);
		}
		// 设置数据库
		db.setJarPath(jarpath);
		db.load();
		// 设置爬虫
		Crawler crawler = new Crawler(config, db);
		crawler.setJarPath(jarpath);
		String cookie = config.getString("cookie");
		crawler.addCookie("PHPSESSID", cookie);
		String url = config.getString("startpage");
		while (true) {
			url = crawler.resolveListPage(url);
			System.out.println("已完成当前列表并成功获取到下一页: " + url);
		}
	}

}
