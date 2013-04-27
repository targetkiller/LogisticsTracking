function dialog(){
	var overAll;
	var bodyHeight;
	var bodyWidth;

	var dialog;
	var dialogTop;
	var dialogLeft;

	var logo;
	var logoTop;
	var logoLeft;

	overAll = document.getElementById("overall");
	overAll.style.display = "block";
	bodyHeight = document.body.clientHeight;
	bodyWidth = document.body.clientWidth;
	overAll.style.height = bodyHeight+"px";
	overAll.style.width = bodyWidth+"px";
	
	
	dialog = document.getElementById("alert");
	dialog.style.display = "block";
	dialogTop = (bodyHeight-dialog.clientHeight)/2;
	dialogLeft = (bodyWidth-dialog.clientWidth)/2;
	dialog.style.top = dialogTop+"px";
	dialog.style.left = dialogLeft+"px";
	

	logo = document.getElementById("logo");
	logo.style.display = "block";

	logoTop = dialogTop+logo.clientHeight/2;
	logoLeft = dialogLeft;
	logo.style.top = logoTop+"px";
	logo.style.left = logoLeft+"px";
}
function disappear(){
	var overAll = document.getElementById("overall");
	overAll.style.display = "none";

	var dialog = document.getElementById("alert");
	dialog.style.display = "none";

	var logo = document.getElementById("logo");
	logo.style.display = "none";
}
function send()
{	
	var flag=false;
	if(document.form.account.value==''){
		alert("帐号不能为空...");
		document.form.account.focus();
	}else if(document.form.password.value==''){
		alert("密码不能为空...");
		document.form.password.focus();
	}else{
    	flag=true;
	}
	return flag;
}
