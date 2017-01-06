/**
 * Created by Yiding Liu on 12/10/2016.
 */
import React from 'react'
import HotelDetailInfo from '../components/Hotel/HotelDetailInfo'
import {connect} from 'react-redux'
import {getHotelDetails,setHotelDetailTab} from '../actions/hotelActions'

const mapStateToProps= state=>{
    //debugger;
    return{
        hotel:state.hotelData.hotel,
        selectedTab:state.hotelData.selectedTab
    }
};

const mapDispatchToProps= dispatch=>{
    return{
        getHotelDetail:(hotelId)=>{
            dispatch(getHotelDetails(hotelId));
        },
        setTab:(selectedTab)=>{
            dispatch(setHotelDetailTab(selectedTab));
        }
    }
};

const HotelDetailContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(HotelDetailInfo);

export default HotelDetailContainer;