# 顧客管理システム
顧客管理システムのバックエンド（基盤）

フレームワーク : Spring(Java)

相関フロントエンドプロジェクト: 
[frontend-react](https://github.com/xon-synapse-i/shigei_cho/tree/main/react-training/frontend)
[frontend-vue3(未完成)](https://github.com/xon-synapse-i/shigei_cho/tree/main/vue3-training-frontend)

## 項目作成について

### DevContainer 作成

- WSL2インストール
- WSL2 でVscode開く
- Ctrl + Pでコマンドパレットを開く →　>Dev Containers Open Folder in Containerを選択しプロジェクトを作りたいフォルダを選択

### Spring Bootプロジェクト作成

- コマンドパレット(command + shift + p)でspringと入力し、Spring Initializr: Create a Gradle Projectを選択する
- バージョン(3.3)と言語(Java)を選択する
- パッケージ名(com.example)とプロジェクト名(backend)を入力する
- パッケージの形式でJarを選択する
- Javaのバージョン(11)を選択する
- 依存関係として、以下を選択する

・Spring Web

・Spring Boot Actuator

・Thymeleaf

・Lombok

- プロジェクトを作成する場所を選択する。


Reactプロジェクト起動:Spring Boot DashboardでRunする（左の拡張機能） → デフォルト[http://localhost:8000](http://localhost:8000)

## メインファイル構成

workspace/...................ルートディレクトリ  

    └── .devcontainer/..........DevContainerの設定ファイルが置かれるディレクトリ  

        ├── devcontainer.json...DevContainerの設定ファイル.Dockerコンテナの構築方法、Visual Studio Codeの設定などが記述されます。 

        └── docker-compose.yml..APPとDBのコンテナの設定

        └── Dockerfile..........APPコンテナ構築用のDockerfile
          

　　　　　

workspace/  

    └── backend/......................................アプリケーションの一番上のディレクトリ

        └── src/main..................................ソースコードが置かれるディレクトリ

            └── java/com/example/backend..............このディレクトリの下でMCV開発を行う

                └── controller/.......................コントローラー層のファイルがここで作成する、Webと業務プログラムの橋渡し

                ├── model/............................モデル層のファイルがここで作成する、処理の中核(DBとのデータ交換)

                ├── service/..........................サービス層のファイルがここで作成する、ビジネスルールに関わる処理に専念する

                ├── repository/.......................レポジトリ層のファイルがここで作成する、データの操作処理(CRUD)

                ├── values/...........................定数の設定ファイルがここで作成

                ├── BackendApplication.java/..........プロジェクトの起動(Main関数起動する)、TimeZone設定も行った

                ├── ContextRefreshedListener.java.....プロジェクト起動完了する際にイベントがここで設定する

                └── SecurityConfig.java...............プロジェクトセキュリティーに関する設定(CORS,CSRF制限解除)

            └── resource..............................リソースファイルがここで作成する

                ├── application.properties............環境変数の設定(SQLとの連携、初期化など)

                └── init.sql..........................DB初期化処理が記載されているSQLコマンドファイル

        └── src/test/java/com/example/backend.........テスト自動化用のテストファイルがここで作成する

作成者 : LilRin