package way.service.util;

public class MsgCode{
	
	//client reuqest***********************************
	//login code
	public final static int LOGIN_CODE = 1;
	public final static int LOGIN_SUCCESS = 101;
	public final static int LOGIN_FAILED = 201;
	
	//regist code
	public final static int REGIST_CODE = 2;
	public final static int REGIST_SUCCESS = 102;
	public final static int REGIST_FAILED = 202;
	
	//user exist check
	public final static int USER_CHECK_CODE = 3;
	public final static int USER_EXISTED = 103;
	public final static int USER_NOT_EXISTED = 203;
	
	//user search code
	public final static int USER_SEARCH_CODE = 4;
	public final static int USER_SEARCH_SUCCESS = 104;
	public final static int USER_SEARCH_EMPTY = 204;
	
	//get user info
	public final static int GET_USER_INFO = 5;
	public final static int GET_USER_INFO_SUCCESS = 105;
	public final static int GET_USER_INFO_FAILED = 205;
	
	//add friend
	public final static int ADD_FRIEND = 11;
	public final static int ADD_FRIEND_SUCCESS = 111;
	public final static int ADD_FRIEND_FAILED = 211;
	
	//get friend request
	public final static int GET_FRIEND_REQ = 12;
	public final static int GET_FRIEND_REQ_SUCCESS = 112;
	public final static int GET_FRIEND_REQ_FAILED = 212;
	
	//operate friend request
	public final static int OPERATE_FRIEND_REQ = 13;
	public final static int OPERATE_FRIEND_REQ_ADD = 113;
	public final static int OPERATE_FRIEND_REQ_IGNORE = 213;
	
	//delete friend
	public final static int DELETE_FRIEND = 14;
	public final static int DELETE_FRIEND_SUCESS = 114;
	public final static int DELETE_FRIEND_FAILED = 214;
	
	//get friends
	public final static int GET_FRIENDS = 15;
	public final static int GET_FRIENDS_SUCCESS = 115;
	public final static int GET_FRIENDS_FAILED = 215;
	
	//update position
	public final static int UPDATE_POSITION = 21;
	public final static int UPDATE_POSITION_SUCCESS = 121;
	public final static int UPDATE_POSITION_FAILED = 221;
	
	//get position
	public final static int GET_POSITION = 22;
	public final static int GET_POSITION_SUCCESS = 122;
	public final static int GET_POSITION_FAILED = 222;
	
	//send chat
	public final static int SEND_CHAT = 31;
	public final static int SEND_CHAT_SUCCESS = 131;
	public final static int SEND_CHAT_FAILED = 231;
	
	//send activity chat
	public final static int SEND_ACTIVITY_CHAT = 32;
	public final static int SEND_ACTIVITY_CHAT_SUCCESS = 132;
	public final static int SEND_ACTIVITY_CHAT_FAILED = 232;
	
	//create activity
	public final static int CREATE_ACTIVITY = 41;
	public final static int CREATE_ACTIVITY_SUCCESS = 141;
	public final static int CREATE_ACTIVITY_FAILED = 241;
	
	//invite to join activity
	public final static int INVITE_TO_ACTIVITY = 42;
	public final static int INVITE_TO_ACTIVITY_SUCCESS = 142;
	public final static int INVITE_TO_ACTIVITY_FAILED = 242;
	
	//get activity members
	public final static int	GET_ACTIVITY_INFO = 43;
	public final static int GET_ACTIVITY_INFO_SUCCESS = 143;
	public final static int GET_ACTIVITY_INFO_FAILED = 243;
	
	//get activities
	public final static int GET_ACTIVITIES = 44;
	public final static int GET_ACTIVITIES_SUCCESS = 144;
	public final static int GET_ACTIVITIES_FAILED = 244;
	
	//get activity invites
	public final static int GET_ACTIVITY_INVITES = 45;
	public final static int GET_ACTIVITY_INVITES_SUCCESS = 145;
	public final static int GET_ACTIVITY_INVITES_FAILED = 245;
	
	//delte member from activity
	public final static int DELETE_ACTIVITY_MEMBER = 46;
	public final static int DELETE_ACTIVITY_MEMBER_SUCCESS = 146;
	public final static int DELETE_ACTIVITY_MEMBER_FAILED = 246;
	
	//operate activity invite
	public final static int OPERATE_ACTIVITY_INVITE = 47;
	public final static int OPERATE_ACTIVITY_INVITE_SUCCESS = 147;
	public final static int OPERATE_ACTIVITY_INVITE_FAILED = 247;
	
	//server request********************************************
	//friend tip
	public final static int PUSH_FRIEND_REQ = 301;
	
	//chat
	public final static int PUSH_CHAT = 311;
	
	//activity chat
	public final static int PUSH_ACTIVITY_CHAT = 312;
	
	//activity invite
	public final static int PUSH_ACTIVITY_INVITE = 321;
	
	//members position
	public final static int PUSH_MEMBERS_POSITION = 322;
	
	//system error********************* 
	public final static int WRONG_PARAMETER = 401;
}