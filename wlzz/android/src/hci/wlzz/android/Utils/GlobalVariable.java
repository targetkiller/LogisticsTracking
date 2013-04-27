package hci.wlzz.android.Utils;

import hci.wlzz.android.Models.Role;
import hci.wlzz.android.Models.Station;
import hci.wlzz.android.Models.StationManager;
import hci.wlzz.android.Models.User;
import hci.wlzz.android.Models.UserInfo;

public class GlobalVariable {

	public static boolean ifSuperAdmin = false;
	public static boolean ifStationAdmin = false;
	public static boolean ifCommonUser = false;
	public static boolean ifmanagerLoginSucceed = false;
	public static boolean ifStationLoginSucceed = false;

	public static Role role = null;
	public static UserInfo userInfo = null;
	public static User user = null;
	public static StationManager manager = null;
	public static Station station = null;

}
