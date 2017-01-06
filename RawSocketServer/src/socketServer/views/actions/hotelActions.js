/**
 * Created by Yiding Liu on 12/1/2016.
 */
import {
    HOTEL_LIST,HOTEL_LIST_BEGIN,HOTEL_LIST_FAILED,HOTEL_LIST_SUCCESS,
    SET_HOTEL_LIST,SET_HOTEL_DETAIL_TAB,SET_HOTEL_RATING,SET_HOTEL_NAME_FILTER,
    GET_NUMBER_OF_HOTELS,GET_NUMBER_OF_HOTELS_FAILED,GET_NUMBER_OF_HOTELS_SUCCESS,
    GET_HOTEL_DETAIL,GET_HOTEL_DETAIL_SUCCESS,GET_HOTEL_DETAIL_FAILED,SET_HOTEL_DETAIL
} from './actionConstant'
import {setTotal} from './pagerActions'
import axios from 'axios';

//API call
export const hotelList=(index,count)=>{
    //debugger;
    return dispatch=> {
        axios.get('/Hotel/getHotelList?index='+index+'&count='+count,{})
            .then((response)=>{
                if(response.data.success==true){
                    dispatch(setHotelList(response.data.data));
                }
                else{
                    dispatch(hotelListSuccess(response));
                }
            })
            .catch((err)=>{
                dispatch(hotelListFailed(err));
                //console.log(err);
            });
    }
};
export const getNumberOfHotels=()=>{
    return dispatch=>{
        axios.get('/Hotel/getNumberOfHotels',{})
            .then(response=>{
                //debugger;
                if(response.data.success==true){
                    dispatch(setTotal(response.data.message));
                }
                else{
                    dispatch(getNumberOfHotelsSuccess(response));
                }
            })
            .catch(err=>{
                dispatch(getNumberOfHotelsFailed(err));
            })
    }
};
export const getHotelDetails=(hotelId)=>{
    return dispatch=>{
        axios.get('/Hotel/getHotel?hotelId='+hotelId,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setHotelDetail(response.data.data));
                }
                else{
                    dispatch(getHotelDetailSuccess(response.data));
                }
            })
            .catch(err=>{
                dispatch(getHotelDetailFailed(err))
            });
    }
};
export const getHotelRating=(hotelId)=>{
    return dispatch=>{
        axios.get("/Review/getRating?hotelId="+hotelId,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setHotelRating(response.data.message));
                }
            })
            .catch(err=>{
                console.log(err);
            })
    }
};
export const getHotelListByName=(name,index,count)=>{
    return dispatch=>{
        dispatch(hotelListBegin());
        axios.get('/Hotel/getHotelListByName?name='+name+'&index='+index+'&count='+count,{})
            .then((response)=>{
                if(response.data.success==true){
                    dispatch(setHotelList(response.data.data));
                }
                else{
                    dispatch(hotelListSuccess(response));
                }
            })
            .catch((err)=>{
                dispatch(hotelListFailed(err));
                //console.log(err);
            });
    }
};
export const getNumberOfHotelsByName=(name)=>{
    return dispatch=>{
        axios.get('/Hotel/getNumberOfHotelsByName?name='+name,{})
            .then(response=>{
                //debugger;
                if(response.data.success==true){
                    dispatch(setTotal(response.data.message));
                }
                else{
                    dispatch(getNumberOfHotelsSuccess(response));
                }
            })
            .catch(err=>{
                dispatch(getNumberOfHotelsFailed(err));
            })
    }
};

//Internal call
export const getHotelDetailFailed=(payload)=>{
    return{
        type:GET_HOTEL_DETAIL_FAILED,
        payload
    }
};
export const getHotelDetailSuccess=(payload)=>{
    return{
        type:GET_HOTEL_DETAIL_SUCCESS,
        payload
    }
};
export const hotelListBegin=()=>{
    return{
        type:HOTEL_LIST_BEGIN
    }
};
export const hotelListSuccess=(payload)=>{
    return{
        type:HOTEL_LIST_SUCCESS,
        payload:payload
    }
};
export const hotelListFailed=(payload)=>{
    return {
        type:HOTEL_LIST_FAILED,
        payload:payload
    }
};
export const getNumberOfHotelsSuccess=(payload)=>{
    return {
        tyep:GET_NUMBER_OF_HOTELS_SUCCESS,
        payload
    }
};
export const getNumberOfHotelsFailed=(payload)=>{
    return {
        tyep:GET_NUMBER_OF_HOTELS_FAILED,
        payload
    }
};
export const setHotelList=(hotelList)=>{
    return{
        type:SET_HOTEL_LIST,
        hotelList:hotelList
    }
};
export const setHotelDetail=(hotelDetail)=>{
    return{
        type:SET_HOTEL_DETAIL,
        hotelDetail
    }
};
export const setHotelDetailTab=(selectedTab)=>{
    return{
        type:SET_HOTEL_DETAIL_TAB,
        selectedTab
    }
};
export const setHotelRating=(rating)=>{
    return{
        type:SET_HOTEL_RATING,
        rating
    }
};
export const setHotelNameFilter=(hotelNameFilter)=>{
    return{
        type:SET_HOTEL_NAME_FILTER,
        hotelNameFilter
    }
};