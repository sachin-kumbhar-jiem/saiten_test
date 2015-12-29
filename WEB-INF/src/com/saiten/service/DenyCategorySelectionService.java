package com.saiten.service;

import java.util.Map;

public interface DenyCategorySelectionService {
	public Map<Short, String> findDenyCategoriesByQuestionSeq(int questionSeq);
}
