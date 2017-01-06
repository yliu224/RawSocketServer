/**
 * Created by Yiding Liu on 12/12/2016.
 */
import {SET_TOURIST_ATTRACTION} from '../actions/actionConstant'

let initialSate={
    touristAttractions:[]
};

const touristAttractionData=(state=initialSate,action)=>{
    switch (action.type){
        case SET_TOURIST_ATTRACTION:
            //debugger;
            return {...state,touristAttractions:action.results};
        default:
            return state;
    }
};

export default touristAttractionData;