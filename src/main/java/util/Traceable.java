package util;

/**
 * トレース・インターフェース
 * 標準出力用にmixinとしての使用。
 * @author sankame
 */
public interface Traceable {

    /**
     * 標準出力
     * @param object 出力対象
     */
    default void out(Object object){
        System.out.println(object);
    }

}
