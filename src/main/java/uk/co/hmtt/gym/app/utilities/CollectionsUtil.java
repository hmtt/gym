package uk.co.hmtt.gym.app.utilities;

import java.util.ArrayList;
import java.util.List;

public class CollectionsUtil {

    private CollectionsUtil() {

    }

    public static <T> List<T> safeIterator(List<T> collection) {
        return collection.isEmpty() ? new ArrayList<>() : collection;
    }

}
