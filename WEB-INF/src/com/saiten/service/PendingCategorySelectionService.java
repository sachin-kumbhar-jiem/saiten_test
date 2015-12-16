package com.saiten.service;

import java.util.Map;

public interface PendingCategorySelectionService {
	
	public Map<Short, String> findPendingCategoriesByQuestionSeq(int questionSeq);
	
}
