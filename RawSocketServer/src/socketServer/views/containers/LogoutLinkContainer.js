/**
 * Created by Yiding Liu on 11/30/2016.
 */
import { connect } from 'react-redux'
import { LOGOUT } from '../actions/actionConstant'
import {logout} from '../actions/userActions'
import LogoutLink from '../components/Headers/LogoutLink'

const mapStateToProps = (state) => {
    //debugger;
    return {}
}

const mapDispatchToProps = (dispatch) => {
    return {
        onClick: () => {
            dispatch(logout())
        }
    }
}

const LogoutLinkContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(LogoutLink);

export default LogoutLinkContainer