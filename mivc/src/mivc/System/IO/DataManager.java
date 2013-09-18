/**
 * DataManager.java
 */
package mivc.System.IO;

/**
 * Defines basic operations for use by classes that do IO operations
 * @author Ty
 *
 */
interface DataManager {
    Object read(String path);
    void write(String path, Object obj);
}
