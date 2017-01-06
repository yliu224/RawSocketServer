/**
 * Created by Yiding Liu on 12/11/2016.
 */
import AddOrEditReviewButton from '../components/Review/AddOrEditReviewButton'
import { connect } from 'react-redux'
import {deleteReview} from '../actions/reveiwActions'

const mapStateToProps = (state) => {
    //debugger;
    //index:state.pagerData.index,
    return {
        review: state.reviewData.review,
        orderBy: state.reviewData.orderBy,
        hotel:state.hotelData.hotel,
        pagerInfo:state.pagerData,
        fetchingReview:state.reviewData.fetchingReview
    }
};

const mapDispatchToProps = (dispatch) => {
    return {
        deleteReview:(reviewId,orderBy,index,count,hotel)=>{
            dispatch(deleteReview(reviewId,orderBy,index,count,hotel));
        }
    }
};

const AddOrEditReviewButtonContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(AddOrEditReviewButton);

export default AddOrEditReviewButtonContainer;