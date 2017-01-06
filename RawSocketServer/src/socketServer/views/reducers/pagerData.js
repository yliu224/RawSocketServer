/**
 * Created by Yiding Liu on 12/2/2016.
 */
import {SET_INDEX,SET_TOTAL} from '../actions/actionConstant'

let initialData={
    index:1,
    count:10,
    total:-1
};

const pagerData=(state=initialData,action)=>{
    switch (action.type){
        case SET_TOTAL:
            return {...state,total:action.total};
        case SET_INDEX:
            return {...state,index:action.index};
        default:
            return state;
    }
};
export default pagerData;