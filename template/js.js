function changeBuildNum(c_name, num) {
	var table;
	table = document.getElementById("ct_" + c_name);
	var row = table.rows.length;
	table.rows[row - 1].cells[3].innerHTML = num;
}

function changeWriteStatus(c_name, status) {
	var table;
	table = document.getElementById("ct_" + c_name);
	var row = table.rows.length;
	table.rows[row - 1].cells[2].innerHTML = status;
}

function changeCaseStatus(c_name, status) {
	var table;
	table = document.getElementById("ct_" + c_name);
	var row = table.rows.length;
	table.rows[row - 1].cells[1].innerHTML = status;
}

function addErrorCase(c_name, e_name) {
	var table;
	table = document.getElementById("ct_" + c_name);
	var tr = table.insertRow(table.rows.length);
	for (var i = 0; i < 4; i++) {
		var td = tr.insertCell(i);
		if (i == 0)
			td.innerHTML = e_name;
		if (i == 1)
			td.innerHTML = "";
		if (i == 2)
			td.innerHTML = "";
		if (i == 3)
			td.innerHTML = "";
	}
}

function addComponent(c_name) {
	var e;
	var component_name = c_name;
	var case_num = 10;
	e = document.getElementById("details");
	var c_div = document.createElement("div");
	c_div.setAttribute("id", component_name);
	e.appendChild(c_div);

	var c_head = document.createElement("h3");
	c_head.onclick = function() {
		isDisplayTable("ct_" + c_name);
	}
	c_head.innerHTML = component_name;
	c_div.appendChild(c_head);

	var c_table = document.createElement("table");
	c_table.setAttribute("border", "1")
	c_table.setAttribute("id", "ct_" + c_name);
	c_table.setAttribute("style", "");
	c_div.appendChild(c_table);

	var table = document.getElementById("ct_" + c_name);
	var tr = table.insertRow(table.rows.length);
	for (var j = 0; j < 4; j++) {
		var td = tr.insertCell(j);
		if (j == 0)
			td.innerHTML = "Case-Name";
		if (j == 1)
			td.innerHTML = "Case-Status";
		if (j == 2)
			td.innerHTML = "Write-Status";
		if (j == 3)
			td.innerHTML = "Build-Num";
	}

}

function isDisplayTable(table_id) {
	var table = document.getElementById(table_id);
	var flag = table.getAttribute("style");
	if (flag != null && flag == "")
		table.setAttribute("style", "display: none;");
	else
		table.setAttribute("style", "");
}

function closePassTable() {
	var table_list = document.getElementsByTagName("tbody");
	for (var i = 0; i < table_list.length; i++) {
		var tbody = table_list[i];
		if (tbody.childNodes.length == 1) {
			tbody.parentNode.setAttribute("style", "display: none;");
		} else {
			tbody.parentNode.parentNode.setAttribute("style",
					"background:yellow;")
		}
	}
}
