/**
 * Created by Yiding Liu on 12/1/2016.
 */
import {HOTEL_LIST,HOTEL_LIST_BEGIN,HOTEL_LIST_FAILED,HOTEL_LIST_SUCCESS,
    SET_HOTEL_LIST,SET_HOTEL_DETAIL,SET_HOTEL_DETAIL_TAB,SET_HOTEL_RATING,SET_HOTEL_NAME_FILTER,
    GET_NUMBER_OF_HOTELS_FAILED,GET_NUMBER_OF_HOTELS_SUCCESS,
    GET_HOTEL_DETAIL_FAILED,GET_HOTEL_DETAIL_SUCCESS
} from '../actions/actionConstant'

let initialState={
    firstLoad:true,
    fetching:false,
    fetched:false,
    response:null,
    hotelList:[],
    hotel:null,
    selectedTab:'reviews',
    hotelNameFilter:""
};
const successData=(state,action)=>({
    ...state,fetching:false,fectch:true,response:action.payload.data
});
const failedData=(state,action)=>({
    ...state,fetching:false,fetched:true,response:action.payload
});
const hotelData =(state=initialState,action)=>{
    //debugger;
    switch (action.type){
        case HOTEL_LIST_BEGIN:
            return {...state,fetching:true,fetched:false};
        case HOTEL_LIST_FAILED:
            return failedData(state,action);
        case HOTEL_LIST_SUCCESS:
            return successData(state,action);
        case SET_HOTEL_LIST:
            return {...state,firstLoad:false,fetching:false,hotelList:action.hotelList};
        case GET_NUMBER_OF_HOTELS_FAILED:
            return failedData(state,action);
        case GET_NUMBER_OF_HOTELS_SUCCESS:
            return successData(state,action);
        case GET_HOTEL_DETAIL_FAILED:
            return failedData(state,action);
        case GET_HOTEL_DETAIL_SUCCESS:
            return successData(state,action);
        case SET_HOTEL_DETAIL:
            return {...state,hotel:action.hotelDetail};
        case SET_HOTEL_DETAIL_TAB:
            return {...state,selectedTab:action.selectedTab};
        case SET_HOTEL_RATING:
            //debugger;
            let hotel=state.hotel;
            return {...state,hotel:{
                addr:hotel.addr,
                city:hotel.city,
                country:hotel.country,
                hotelId:hotel.hotelId,
                imgUrl:hotel.imgUrl,
                lat:hotel.lat,
                lng:hotel.lng,
                name:hotel.name,
                rating:action.rating,
                state:hotel.state
            }};
        case SET_HOTEL_NAME_FILTER:
            return {...state,hotelNameFilter:action.hotelNameFilter};
        default:
            return state;
    }
};
export default hotelData;