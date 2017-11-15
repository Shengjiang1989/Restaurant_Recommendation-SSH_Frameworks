package dao;

import java.util.Collection;
import java.util.Set;

public interface RecommendationDAO {

	Set<String> getCategories(String restaurant);

	Set<String> getBusinessId(String category);

}
