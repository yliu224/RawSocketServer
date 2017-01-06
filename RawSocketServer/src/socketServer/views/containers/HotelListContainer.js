/**
 * Created by Yiding Liu on 12/1/2016.
 */
import React from 'react'
import HotelList from '../components/Hotel/HotelList'
import { connect } from 'react-redux'
import {hotelList,getNumberOfHotels,setHotelNameFilter,getHotelListByName,getNumberOfHotelsByName} from '../actions/hotelActions'
import {setIndex} from '../actions/pagerActions'

const mapStateToProps = (state) => {
    //debugger;
    //index:state.pagerData.index,
    return {
        hotelList: state.hotelData.hotelList,
        count:state.pagerData.count,
        fetching:state.hotelData.fetching,
        firstLoad:state.hotelData.firstLoad
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        getHotelList: (index,count) => {
            dispatch(hotelList(index,count))
        },
        getNumberOfHotels:()=> {
            dispatch(getNumberOfHotels())
        },
        setPagerIndex:(index)=>{
            dispatch(setIndex(index));
        },
        setHotelNameFilter:(name)=>{
            dispatch(setHotelNameFilter(name));
        },
        getHotelListByName:(name,index,count)=>{
            dispatch(getHotelListByName(name,index,count));
        },
        getNumberOfHotelsByName:(name)=>{
            dispatch(getNumberOfHotelsByName(name));
        }
    }
};

const HotelListContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(HotelList);

export default HotelListContainer;