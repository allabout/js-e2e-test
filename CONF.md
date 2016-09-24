# application.conf 記述方法

* application.confを作成後、下記をコピーして値は適宜書き換えて下さい。

    // Selenium Server Standaloneが起動している端末のURL。
    selenium-server-url = "http://127.0.0.1:4444/wd/hub"

    // テスト除外ドメイン
    // ここに定義されたドメインのJSエラーは通知対象外となります。
    exclude-domains = [
                "example1.com"
                , "example2.com"
                , "example3.com"
                ]

    // メール送信フラグ(true:送信する、false:送信しない)
    // trueの場合、本アプリを動かす端末上でSMTPサーバーを起動して下さい。
    mail-send = false

    // メール関連
    mail : {
        from : "mailfrom@example.co.jp"
        , to : "mailto@example.co.jp"
        , charset : "iso-2022-jp"
        , subject-prefix : "[JSエラーチェック]"
        , body-prefix : "担当各位"
        , body-prefix2 : "下記エラーが発生しています。"
    }

    // Webdriverタイムアウト時間(秒)
    webdriver-implicitly-wait = 90
    webdriver-page-load-timeout = 90
