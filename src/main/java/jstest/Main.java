package jstest;

/**
 * エントリポイント
 * @author sankame
 */
public class Main{

	public static void main(String[] args) {
        // 第一引数：アプリ名
        // 第二引数：テスト対象URLを含むファイルへのパス
        // 第三引数：OS
        TestOrganizer testOrganizer = new TestOrganizer(args[0], args[1], args[2]);
        testOrganizer.doTest();
    }

}
