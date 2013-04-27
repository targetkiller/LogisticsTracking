var explain;
var time;
var explainHeight;
function showResult(){
	explain=document.getElementById("explain");
	explainHeight=explain.clientHeight;
	window.clearInterval(time);
	time=window.setInterval("slideUp()",10);
}
function slideUp(){
	explainHeight -= 8;
	if(explainHeight<=0){
		window.clearInterval(time);
		//add answer
		//clearInfo();     //迟点要加上这个方法
		time=setInterval("slideDown()",10);
	}
	explain.style.height=explainHeight+"px";
}
function clearInfo(){
	while(explain.hasChildNodes){
		explain.removeChild(explain.firstChild);
	}
}

function slideDown(){
	explainHeight=explainHeight+8;
	if(explainHeight>=200){
		window.clearInterval(time);
	}
	explain.style.height=explainHeight+"px";
	//console.log(explainHeight);
}