<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
</head>

<body>
	<h1>Struts 2 Subset tag example</h1>
	<s:set var="columnSize" value="4" />
	<s:set var="totalRecords" value="numbers.size()" />
	<s:set var="startValue" value="0"/>
	<table border="1px" cellpadding="8px">

		<s:iterator begin="1" end="%{@com.saiten.util.SaitenUtil@getRowSize(#totalRecords,#columnSize)}" status="rowStatus">
			<tr valign="top">
			    <%--  <td><s:property value="%{#rowStatus.index}"/></td> 
				<td><s:property
						value="%{#rowStatus.count*#columnSize >= #totalRecords?1:0}" /></td>
				<td><s:property value="%{#totalRecords}" /></td> --%>
				
				<s:set var="startValue" value="%{ #rowStatus.index==0 ? #rowStatus.index:#rowStatus.index * #columnSize }"/>
				
				<s:subset source="numbers" start="%{#startValue}" count="%{@com.saiten.util.SaitenUtil@findRecordCount(#totalRecords,#columnSize,#rowStatus.index)}">
						<s:iterator>
							<td><s:property /></td>
						</s:iterator>
					</s:subset>
			</tr>
		</s:iterator>
	</table>

</body>
</html>