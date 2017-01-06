/**
 * Created by Yiding Liu on 11/30/2016.
 */
import {LOGOUT,LOGOUT_BEGIN,LOGOUT_SUCCESS,LOGOUT_FAILED,LOGIN_BEGIN,LOGIN_FAILED,LOGIN_SUCCESS,SET_USER_INFO,GET_USER_INFO_SUCCESS,GET_USER_INFO_FAILED} from '../actions/actionConstant'

let initialSate={
    fetching:false,
    fetched:false,
    response:null,
    user:{
        username:null,
        id:null,
        lastLoginTime:null
    }
};

const successData=(state,action)=>({
    ...state,fetching:false,fectch:true,response:action.payload.data
});
const failedData=(state,action)=>({
    ...state,fetching:false,fetched:true,response:action.payload
});

const userData=(state=initialSate, action)=>{
    switch (action.type){
        case LOGOUT_BEGIN:
            return {...state,fetching:true,fetched:false};
        case LOGOUT_FAILED:
            return failedData(state,action);
        case LOGOUT_SUCCESS:
            return successData(state,action);
        case LOGIN_BEGIN:
            return {...state,fetching:true,fetched:false};
        case LOGIN_FAILED:
            return failedData(state,action);
        case LOGIN_SUCCESS:
            return successData(state,action);
        case GET_USER_INFO_SUCCESS:
            return successData(state,action);
        case GET_USER_INFO_FAILED:
            return failedData(state,action);
        case SET_USER_INFO:
            return {...state,user:action.userinfo};
        default:
            return state;
    }
}
export default userData