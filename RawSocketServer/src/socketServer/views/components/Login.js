/**
 * Created by Yiding Liu on 12/1/2016.
 */
import React from 'react'
import { connect } from 'react-redux'
import {login} from '../actions/userActions'
import cookie from 'react-cookie'
import {browserHistory} from 'react-router'

const mapStateToProps = (state) => {
    return {
        user: state.userData.user,
        response:state.userData.response
    }
};

class Login extends React.Component{
//the parameter in ref is the tag itself

    componentWillMount(){
        //debugger;
        let sessionId=cookie.load("session_id");
        if(sessionId!=undefined){
            //debugger;
            browserHistory.push("/Hotel");
        }
    }
    render(){
        let { dispatch,user,response}=this.props;
        let username=null,password=null;

        let ShowMessage=()=>{
            if(response==null||response==undefined){
                return "";
            }else{
                return(
                    <div id="errorMessage" className="alert alert-danger">{response.message}</div>
                )
            }
        };
        //debugger
        return (
            <div className="container main-box">
                <div className="row">
                    <div className="col-sm-12">
                        <div className="text-center"><h2><strong>LOG IN</strong></h2></div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-sm-12">
                        <div className="form-group">
                            <label htmlFor="username">User Name:</label>
                            <input type="text" className="form-control" id="username" ref={(input)=>{username=input;}}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="username">Password:</label>
                            <input type="password" className="form-control" id="password" ref={(input)=>{password=input;}}/>
                        </div>
                        {ShowMessage(response,dispatch)}
                        <button id="login" type="button" className="btn btn-primary btn-block" onClick={ e =>{
                            e.preventDefault();
                            dispatch(login(username.value,password.value));
                            //dispatch(setUserInfo({username:"DeanChange",userid:2}))
                        }}>Login</button>
                    </div>
                </div>
            </div>
        )
    }
};
Login = connect(mapStateToProps)(Login);

export default Login