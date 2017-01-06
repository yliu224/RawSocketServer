/**
 * Created by Yiding Liu on 11/30/2016.
 */
import React, { PropTypes } from 'react'
import { connect } from 'react-redux'
import {getUserInfo} from '../../actions/userActions'

const mapStateToProps = (state) => {
    return {
        user: state.userData.user
    }
};
const mapDispatchToProps = (dispatch) => {
    return {
        getUserInfo: () => {
            dispatch(getUserInfo())
        }
    }
};
class Avater extends React.Component{
    componentWillMount(){
        this.props.getUserInfo();
    }
    render(){
        return(
            <div className="navbar-header">
                <span className="navbar-brand">Hello,{this.props.user.username}!</span>
                <span className="navbar-brand" style={{fontSize:'12px'}}>{this.props.user.lastLoginTime}</span>
            </div>
        );
    }
}
Avater = connect(mapStateToProps,mapDispatchToProps)(Avater);
export default Avater;