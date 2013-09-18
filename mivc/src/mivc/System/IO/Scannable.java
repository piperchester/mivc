/**
 * Scannable.java
 */
package mivc.System.IO;

import java.util.List;

/**
 * Defines scanning operation(s) for use by classes that use IO operations.
 * @author Ty
 *
 */
public interface Scannable<E> {
    List<E> scan(String path);
}
