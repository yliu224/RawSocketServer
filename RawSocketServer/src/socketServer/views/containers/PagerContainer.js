/**
 * Created by Yiding Liu on 12/2/2016.
 */
import Pagers from '../components/Pager/Pagers'
import { connect } from 'react-redux'
import {setIndex} from '../actions/pagerActions'

const mapStateToProps = (state,props) => {
    //debugger;
    return {
        index:state.pagerData.index,
        total:state.pagerData.total,
        count:state.pagerData.count,
        orderBy:state.reviewData.orderBy,
        updateChild:props.updateChild,
        hotelNameFilter:state.hotelData.hotelNameFilter,
        userName:state.userData.userName
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        setIndex: (index) => {
            dispatch(setIndex(index))
        }
    }
};

const PagerContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(Pagers);

export default PagerContainer;