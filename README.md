# 概要

Selenium WebDriverを用いてJavaScript(以下JS)の自動テストを行います。

# 動作環境

* [OS] MacOS X, Windows7
* [Java] 1.8.0_65
* [Gradle] 2.9
* [Selenium Server Standalone] 2.53.0
* [Firefox(テスト用ブラウザ)] 44.0

# 準備A (アプリを実行する端末)

### ※Macの場合、準備A・Bが同一端末でも可

- ソースコード  
    - 本ソースコードをクローンします。
    - configディレクトリにapplication.configを作成します。
    - [記述方法はこちら](/CONF.md)  

- Java  
    - JDK8をインストールします。  

- Gradle  
    - ビルドツールとしてGradleをインストールします。
    - (Macの場合) sdkmanを使います。

        $ curl -s "https://get.sdkman.io" | bash

        最後に "sdkman-init.sh" を実行する旨のメッセージが表示されるので、そのままコマンドラインで実行。
        
        さらに下記コマンドで利用可能なバージョンを指定してGradleをインストール。
        
        $ gvm list gradle  
        $ gvm install gradle 2.9
        
    - (Windowsの場合) 下記URLの"Binary only distribution"から取得します。

        https://gradle.org/gradle-download/

# 準備B (ブラウザを起動する端末)

- Java  
    - JDK8をインストールします。  

- Selenium Server Standalone  
    - 下記URLから selenium-server-standalone-2.53.0.jar をダウンロードします。
    - http://selenium-release.storage.googleapis.com/index.html?path=2.53/
    - 下記コマンドで起動します。

        $ java -jar selenium-server-standalone-2.53.0.jar

- Firefox  
    - WebDriverが最新のFirefoxに未対応のため、Ver.44以前のものをインストールします。  
    (自分のOSに合ったものを選択)  
    https://ftp.mozilla.org/pub/firefox/releases/44.0/  
    
    - テストに使うため自動バージョンアップを停止しておきます。
    
        「ツール(T)」-「オプション(O)」(Macの場合は「環境設定」)
        
        「詳細」の「更新」タブを開き、「更新の確認は行うが、インストールするかどうかを選択する」に設定。

# ビルド

- ソースコードのルートディレクトリで下記コマンドを実行。

    $ gradle distZip

- build/distributions/ にzipが作成されます。

- 解凍して下記コマンドでアプリを実行可能です。
    
    $sh bin/js-e2e-test [テスト対象アプリ名(任意)] [テスト対象URL(*1)] [OS(mac or windows)]

    (*1) テスト対象のURLを改行区切りでファイルに記載。そのファイルへのパスを指定。

        http://test-target.com/
        http://test-target.com/sample1/
        http://test-target.com/sample2/

    (コマンド例)
    
    $sh bin/js-e2e-test weather-report /Users/tester/urls.txt mac

- Firefoxが起動し、(*1)のURLに順次アクセスします。

# 実行方法

- ソースコードのルートディレクトリで下記コマンドを実行。

    $ gradle run -Pargs="[テスト対象アプリ名(任意)] [テスト対象URL(*1)] [OS(mac or windows)]"

# ユニットテスト

準備中。

# 作成者

**Copyright © 2016 All About, Inc.**

# ライセンス

本ソースコードはMITライセンスの元に公開しています。
