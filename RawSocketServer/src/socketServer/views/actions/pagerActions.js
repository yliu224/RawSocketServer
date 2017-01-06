/**
 * Created by Yiding Liu on 12/2/2016.
 */
import {SET_INDEX,SET_TOTAL} from './actionConstant'

export const setIndex=(index)=>{
    return {
        type:SET_INDEX,
        index
    }
};
export const setTotal=(total)=>{
    return{
        type:SET_TOTAL,
        total
    }
};