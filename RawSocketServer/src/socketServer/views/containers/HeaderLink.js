/**
 * Created by Yiding Liu on 11/30/2016.
 */
import { connect } from 'react-redux'
import { LOGOUT } from '../actions/actionConstant'
import {logout} from '../actions/userActions'
import Link from '../components/Headers/Link'

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

const FilterLink = connect(
    mapStateToProps,
    mapDispatchToProps
)(Link)

export default FilterLink