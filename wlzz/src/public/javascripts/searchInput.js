var input;
function fcousValue(){
	input=document.getElementById("input");
	if(input.value=="请输入搜索条件搜索订单"){
		input.value="";
	}
	input.style.color="#000000";
}
function blurValue(){
	input=document.getElementById("input");
	if(input.value==""){
		input.value="请输入搜索条件搜索订单";
		input.style.color="#bababa";
	}

}