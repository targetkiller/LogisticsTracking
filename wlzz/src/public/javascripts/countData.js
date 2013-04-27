function ini(){
	var data=document.getElementById("data");
	data.style.backgroundColor="#1391f5";
	data.style.fontSize="16px";
	data.style.color="#ffffff";
	data.style.fontWeight="bold";

	var dataform=document.getElementById("dataform");
	dataform.style.display="block";
	var chartform=document.getElementById("chartform");
	chartform.style.display="none";
}

function accountData(){
	var data=document.getElementById("data");
	data.style.backgroundColor="#1391f5";
	data.style.fontSize="16px";
	data.style.color="#ffffff";
	data.style.fontWeight="bold";

	var dataform=document.getElementById("dataform");
	dataform.style.display="block";

	var charts=document.getElementById("charts");
	charts.style.backgroundColor="#f4f8fc";
	charts.style.fontSize="12px";
	charts.style.color="#000000";
	charts.style.fontWeight="normal";

	var dataform=document.getElementById("dataform");
	dataform.style.display="block";
	var chartform=document.getElementById("chartform");
	chartform.style.display="none";

}
function accountCharts(){
	var charts=document.getElementById("charts");
	charts.style.backgroundColor="#1391f5";
	charts.style.fontSize="16px";
	charts.style.color="#ffffff";
	charts.style.fontWeight="bold";

	var data=document.getElementById("data");
	data.style.backgroundColor="#f4f8fc";
	data.style.fontSize="12px";
	data.style.color="#000000";
	data.style.fontWeight="normal";

	var dataform=document.getElementById("dataform");
	dataform.style.display="none";
	var chartform=document.getElementById("chartform");
	chartform.style.display="block";
}

function stationInput(){
	var checkbox=document.getElementById("checkbox");
	var station=document.getElementById("station");
	var status=checkbox.checked;
	if(status==true){
		station.disabled=false;
	}
	else{
		station.disabled=true;
	}
}

function chart(typeParam){
    var B = bt();
    var A = at();
    var type = typeParam;
    var dataType = document.getElementById("unit").value;
    var countProxy = document.getElementById("unitType").value;
    var account = document.getElementById("stationInput").value;
    var hosturl = "@{Root.chartOrder}";
    alert(A+B+type+dataType+countProxy+account+hosturl);
    $.post(
    hosturl,
    {"firstTime":B,"lastTime":A,"chartType":type,"dataType":dataType,"stationId":account,"proxy":countProxy},
    function(mgs){
        detailDiv = document.getElementById("soga");
        detailDiv.src = mgs;
    }, "text");
}


function bt(){
    var CBY = document.getElementById("chartBYear").value;
    var CBM = document.getElementById("chartBMonth").value;
    var CBD = document.getElementById("chartBDay").value;
    var CB = CBY+CBM+CBD;
    return CB;
}
function at(fromParam){
    var CAY = document.getElementById("chartAYear").value;
    var CAM = document.getElementById("chartAMonth").value;
    var CAD = document.getElementById("chartADay").value;
    var CA = CAY+CAM+CAD;
    return CA;
}