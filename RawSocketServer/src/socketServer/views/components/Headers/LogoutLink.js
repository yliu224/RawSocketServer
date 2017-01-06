/**
 * Created by Yiding Liu on 11/30/2016.
 */
import {logout} from '../../actions/userActions'
import React, { PropTypes } from 'react'

class LogoutLink extends React.Component{
    render(){
        let {onClick,children}=this.props;
        return(
            <a href="#"
               onClick={e =>{
                e.preventDefault();
                //debugger;
                onClick();
            }} >{children}</a>
        );
    }
}
export default LogoutLink