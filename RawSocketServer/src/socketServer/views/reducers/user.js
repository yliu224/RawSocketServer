/**
 * Created by Yiding Liu on 11/30/2016.
 */
import {LOGOUT,LOGOUT_BEGIN,LOGOUT_SUCCESS,LOGOUT_FAILED} from '../actions/actionConstant'

let initialSate={
    fetching:false,
    fetched:false,
    payload:null,
    logout:false
}

const user=(state=initialSate, action)=>{
    switch (action.type){
        case LOGOUT_BEGIN:
            return {...state,fetching:true,fetched:false}
        case LOGOUT_FAILED:
            return {...state,fetching:false,fetched:true,payload:action.payload}
        case LOGOUT_SUCCESS:
            return {...state,fetching:false,fectch:true,payload:action.payload}
        default:
            return state;
    }
}
export default user