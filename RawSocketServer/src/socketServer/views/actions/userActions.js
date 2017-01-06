/**
 * Created by Yiding Liu on 11/28/2016.
 */
import {LOGOUT,LOGOUT_BEGIN,LOGOUT_FAILED,LOGOUT_SUCCESS,LOGIN,LOGIN_BEGIN,
    LOGIN_SUCCESS,LOGIN_FAILED,SET_USER_INFO,GET_USER_INFO_FAILED,GET_USER_INFO_SUCCESS} from './actionConstant'
import axios from 'axios'
import { browserHistory } from 'react-router'
import cookie from 'react-cookie'

//logout
export const logout=() =>{
    return dispatch=> {
        dispatch(logoutBegin());
        axios.post('/Home/logout', {})
            .then((response)=>{
                dispatch(logoutSuccess(response));
                //console.log(response);
            })
            .catch((err)=>{
                dispatch(logoutFailed(err));
                //console.log(err);
            });
    }
};
export const logoutSuccess=(payload)=>{
    //debugger;
    cookie.remove("session_id",{ path: '/' });
    let data=payload.data;
    if(data.success==true){
        window.location.replace("/");
    }
    else{
        alert(payload.stateText);
    }

    return{
        type:LOGOUT_SUCCESS,
        payload:payload
    }
};
export const logoutBegin=()=>{
    return{
        type:LOGOUT_BEGIN
    }
};
export const logoutFailed=(payload)=>{
    return{
        type:LOGOUT_FAILED,
        payload:payload
    }
};

//login
export const login=(username,password)=>{
    return dispatch =>{
        dispatch(loginBegin());

        axios.post('/Home/login',{username:username,password:password})
        .then(response=>{
                if(response.data.success==true){
                    dispatch(setUserInfo(response.data.data));
                    browserHistory.push('/Hotel');
                }
                else{
                    dispatch(loginSuccess(response));
                }
            })
            .catch(erro=>{
                dispatch(loginFailed(erro));
            })
    }
};
export const loginSuccess=(payload)=>{
    return{
        type:LOGIN_SUCCESS,
        payload:payload
    }
};
export const loginBegin=()=>{
    return{
        type:LOGIN_BEGIN
    }
};
export const loginFailed=(payload)=>{
    return{
        type:LOGIN_FAILED,
        payload:payload
    }
};

//user
export const setUserInfo=(userinfo)=>{
    return {
        type:SET_USER_INFO,
        userinfo:userinfo
    }
};
export const getUserInfo=()=>{
    return dispatch=>{
        axios.get('/Home/getUserInfo', {})
            .then((response)=>{
                if(response.data.success==true){
                    dispatch(setUserInfo(response.data.data));
                }
                else{
                    //dispatch(getUserInfoSuccess(response));
                    cookie.remove("session_id",{ path: '/' });
                    window.location.replace("/Home/login");
                }
            })
            .catch((err)=>{
                dispatch(getUserInfoFailed(err));
                //console.log(err);
            });
    }
};
export const getUserInfoSuccess=(payload)=>{
    return{
        type:GET_USER_INFO_SUCCESS,
        payload
    }
};
export const getUserInfoFailed=(payload)=>{
    return{
        type:GET_USER_INFO_FAILED,
        payload
    }
};