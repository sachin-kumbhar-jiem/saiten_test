package com.saiten.util;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;
import org.displaytag.util.HtmlAttributeMap;

import com.saiten.info.DailyStatusReportListInfo;

public class SaitenDecoretor extends TableDecorator {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		HeaderCell oneHeader;
		List headersList = tableModel.getHeaderCellList();
		for (int i = 0; i < headersList.size(); i++) {
			oneHeader = (HeaderCell) headersList.get(i);
			HtmlAttributeMap oneHtmlAttributeMap = oneHeader
					.getHeaderAttributes();
			oneHtmlAttributeMap.put("colSpan", "3");
			oneHeader.setHtmlAttributes(oneHtmlAttributeMap);
		}
	}

	public String getFirstTimeScorer() {
		DailyStatusReportListInfo dailyStatusReportListInfo = (DailyStatusReportListInfo) getCurrentRowObject();
		String firstTimeScorerStr = "<table style='width:100%'><tr><td style='width:30%'>"
				+ dailyStatusReportListInfo.getFirstTimeScoringWait()
				+ "</td>"
				+ "<td style='width:30%'>"
				+ dailyStatusReportListInfo.getFirstTimeScoringTemp()
				+ "</td>"
				+ "<td style='width:30%'>"
				+ dailyStatusReportListInfo.getFirstTimeScoringPending()
				+ "</td>" + "</tr></table>";
		return firstTimeScorerStr;
	}
}
