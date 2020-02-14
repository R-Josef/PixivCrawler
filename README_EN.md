# PixivCrawler

This is a crawler program for [pixiv](https://www.pixiv.net/), written in Java.

Support crawling daily leaderboards, male/female hot leaderboards, newcomer leaderboards, original work leaderboards and their sub-categories, including R18 mode.

## FEATURES

- Leaderboards crawl support.
- Proxy support.
- YAML format.
- SQLite database record the pictures that have been crawled to ensure that the pictures are not downloaded repeatedly.

## LIBRARIES

1. [Jsoup](https://jsoup.org/)
2. [SQLite](https://github.com/xerial/sqlite-jdbc)
3. [SnakeYaml](https://bitbucket.org/asomov/snakeyaml/src/default/)
4. [fastjson](https://github.com/alibaba/fastjson)

## USAGE

Prepare: a Java runtime environment, a browser that can view cookies(Chrome as a example), a network environment that can access pixiv or a proxy that can access pixiv, a pixiv account.

1. open Chrome.
2. access www.pixiv.net, and log in.
3. press F12 to bring up the DevTools. Find and select Application in the upper right corner(might hide in '>>').
4. select Cookies-https://www.pixiv.net on the left and you will see a form, this is cookies.
5. copy the value of PHPSESSID.
6. run the program once, the config will be automatically generated, open it.
7. set host and port in proxy(unless you can access directly).
8. paste the value you copy on step 5.
9. set a leaderboards page's link you like as the page to start crawling under startpage.
10. run the program through the command or the attached startup script (run.bat / run.sh).

## CONFIG:

```yaml
#if you don't need proxy, just set ''.
proxy:
  host: '127.0.0.1'
  port: '10809'

#find value of PHPSESSID in the cookies of pixiv.
cookie: 'YOUR-COOKIE-HERE'

#this page link will be the start page when crawlering.
#this link will be changed when program running, and will stop on the last page you had crawlerd.
startpage: 'https://www.pixiv.net/ranking.php?mode=male'

#picture file storage path, %HERE% is the folder of program's jar.
imagesavepath: '%HERE%/Crawled'
```

