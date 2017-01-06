/**
 * Created by Yiding Liu on 11/28/2016.
 */
import {combineReducers} from 'redux'
import userData from './userData'
import hotelData from './hotelData'
import pagerData from './pagerData'
import reviewData from './reviewData'
import touristAttractionData from './touristAttractionData'

const AppReducer=combineReducers({
    userData,
    hotelData,
    pagerData,
    reviewData,
    touristAttractionData
});

export default AppReducer