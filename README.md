# PixivCrawler

这是一个插画网站[pixiv](https://www.pixiv.net/)的爬虫程序, 采用Java语言编写.

支持爬取每日排行榜, 男/女热门排行榜, 新人排行榜, 原创作品排行榜和其子分类, 包括R18模式.

## 特性

- 支持爬取各种排行榜
- 支持代理
- YAML格式的配置文件
- 采用SQLite数据库记录已经爬取过的图片, 确保图片不重复下载

## 用到的第三方库

1. [Jsoup](https://jsoup.org/)
2. [SQLite](https://github.com/xerial/sqlite-jdbc)
3. [SnakeYaml](https://bitbucket.org/asomov/snakeyaml/src/default/)
4. [fastjson](https://github.com/alibaba/fastjson)

## 启动

事前准备: 安装好Java运行环境; 一个能查看cookie的浏览器 (这里以Chrome为例); 一个能访问pixiv的网络环境, 或者一个能访问pixiv的代理; 一个pixiv账号;

1. 打开Chrome浏览器
2. 访问www.pixiv.net, 并登录
3. 按下F12调出调试界面, 在界面的右上角找到并选中Application, 如果没有看见请点击旁边的">>"
4. 在左侧选中Cookies-https://www.pixiv.net 你将会看到一个表格, 这个就是cookies
5. 复制PHPSESSID的值备用
6. 运行一次程序, 将自动生成配置文件, 打开它
7. 在代理设置 (proxy) 中, 设置好主机 (host) 和端口 (port) 
8. 将刚刚复制到的cookie粘贴至cookie下
9. 在startpage下设置一个你喜欢的页面作为开始爬取的页面
10. 通过命令或附带的启动脚本 (run.bat/run.sh) 运行程序, 开始爬取图片吧

## 配置文件示例:

```yaml
#代理设置, 如果无需使用代理请用两个单引号''表示留空
proxy:
  host: '127.0.0.1'
  port: '10809'

#找到www.pixiv.net网站cookie中'PHPSESSID'键的值填入此项
cookie: 'YOUR-COOKIE-HERE'

#爬虫程序运行时, 将使用此页面作为开始页面
#爬虫程序会在运行时，不断写入此项，以便于下次运行时接着爬取
startpage: 'https://www.pixiv.net/ranking.php?mode=male'

#图片文件的储存路径, %HERE%会被程序自动替换成jar文件所在的文件夹
imagesavepath: '%HERE%/Crawled'
```

