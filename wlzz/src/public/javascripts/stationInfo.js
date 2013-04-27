function ini(){
	var checkbox=document.getElementById("checkbox");
	var idinput=document.getElementById("idinput");
	var submit=document.getElementById("submit");
	checkbox.checked=false;
	idinput.disabled=true;
	submit.style.display="none";

}
function checkBoxEvent(){
	var checkbox=document.getElementById("checkbox");
	var mark=checkbox.checked;
	var idinput=document.getElementById("idinput");
	var submit=document.getElementById("submit");
	if(mark==true){
		idinput.disabled=false;
		submit.style.display="inline";
	}
	else{
		idinput.disabled=true;
		submit.style.display="none";
	}
}