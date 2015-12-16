window.onload = function() {
	var g = new html5jp.graph.vbar("chart");
	if( ! g ) { return; }
	var items = [
		["平均点", 152.1, 134.2, 145.2, 123.7, 123.6, 120.1, 141.5, 131.6, 123.3 ]
	];
	var params = {
		x: ["クラス", "A組", "B組", "C組", "D組", "E組", "F組", "G組", "H組", "I組"],
		y: ["平均点(点)"],
		yMax: 200,
		barColors: ["#f9c526"],
		barGradation: true,
		gBackgroundColor: "#EEEEEE",
		gGradation: false,	
		barAlpha: 0.9,
		barShape: "flat",
		legend: false
	};
	g.draw(items, params);
};
