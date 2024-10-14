package commandManager.commands;

/**
 * Provides Argument Consumer
 *
 * @param <T> Argument param
 * @author nikita
 * @since 2.1
 */
public interface ArgumentConsumer<T> {
    void setObj(T obj);
}