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
	
	//server request********************************************
	//friend tip
	public final static int PUSH_FRIEND_REQ = 301;
	
	//system error*********************
	public final static int WRONG_PARAMETER = 401;
}