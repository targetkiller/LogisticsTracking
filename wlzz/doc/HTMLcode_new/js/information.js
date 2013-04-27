function onFocus (obj,text) {
	// body...
	obj.style.borderColor="#468ee3";
	obj.style.borderWidth="2px";
	var element=document.getElementById(text);
	element.style.visibility="visible";
}
function onBlur(obj,text) {
	// body...
	obj.style.borderColor="#afafaf";
	obj.style.borderWidth="1px";
	var element=document.getElementById(text);
	element.style.visibility="hidden";
}