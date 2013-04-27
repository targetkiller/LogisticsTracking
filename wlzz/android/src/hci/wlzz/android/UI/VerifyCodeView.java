package hci.wlzz.android.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class VerifyCodeView extends LinearLayout{
	private VerifyCode vc=null;
	private EditText et=null;

	public VerifyCodeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.verifycodeview, this);
		Inint();
	}
	public void Inint(){
		vc=(VerifyCode)findViewById(R.id.verifycode_code);

		et=(EditText)findViewById(R.id.verifycodeview_input);
	}
	
	public boolean checkVerifyCode(){
		hideKeyboard(et);
		if(et.getText().toString().equals(vc.getString()))
			return true;
		else
			return false;
	}
	public void hideKeyboard(EditText input){
//		���������
		InputMethodManager imm=(InputMethodManager)this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	}
}
