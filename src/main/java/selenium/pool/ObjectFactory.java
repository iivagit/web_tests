package selenium.pool;

import java.util.List;

public interface ObjectFactory<T> {

	/**
	 * Returns a new instance of an object type T.
	 * 
	 * @return T an new instance of the object type T
	 */
	List<T> createList();
}
