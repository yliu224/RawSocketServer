/**
 * Created by Yiding Liu on 12/11/2016.
 */
import AddOrEditReviewInfo from '../components/Review/AddOrEditReviewInfo'
import { connect } from 'react-redux'
import {getReview,addReview,editReview} from '../actions/reveiwActions'

const mapStateToProps = (state,props) => {
    //debugger;
    //index:state.pagerData.index,
    return {
        review: state.reviewData.review,
        orderBy:state.reviewData.orderBy,
        hotelId:props.params.hotelId,
        userName:state.userData.user.username,
        index:state.pagerData.index,
        count:state.pagerData.count
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        getReview:(hotelId,userName)=>{
            dispatch(getReview(hotelId,userName));
        },
        addReview:(reviewJson,orderBy,index,count,userName)=>{
            dispatch(addReview(reviewJson,orderBy,index,count,userName));
        },
        editReview:(reviewJson,orderBy,index,count,userName)=>{
            dispatch(editReview(reviewJson,orderBy,index,count,userName));
        }
    }
};

const AddOrEditReviewInfoContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(AddOrEditReviewInfo);

export default AddOrEditReviewInfoContainer;