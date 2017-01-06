/**
 * Created by Yiding Liu on 12/11/2016.
 */
import ReviewList from '../components/Review/ReviewList'
import {connect} from 'react-redux'
import {getReviewList,getNumberOfReviews,likeReview,setOrderBy,disLikeReview} from '../actions/reveiwActions'
import {setIndex} from '../actions/pagerActions'

const mapStateToProps= (state,props) =>{
    //index:state.pagerData.index,
    //debugger;
    return{
        reviewList:state.reviewData.reviewList,
        orderBy:state.reviewData.orderBy,
        index:state.pagerData.index,
        count:state.pagerData.count,
        hotelId:props.params.hotelId,
        fetchingReviewList:state.reviewData.fetchingReviewList,
        userName:state.userData.user.username
    }
};
const mapDispatchToProps = dispatch =>{
    return {
        getReviewList:(hotelId,orderBy,index,count,userName)=>{
            dispatch(getReviewList(hotelId,orderBy,index,count,userName));
        },
        getNumberOfReviews:(hotelId)=>{
            dispatch(getNumberOfReviews(hotelId));
        },
        setPagerIndex:(index)=>{
            dispatch(setIndex(index));
        },
        likeReview:(reviewId,index,userName)=>{
            dispatch(likeReview(reviewId,index,userName));
        },
        dislikeReview:(reviewId,index,userName)=>{
            dispatch(disLikeReview(reviewId,index,userName));
        },
        setOrderBy:(orderBy)=>{
            dispatch(setOrderBy(orderBy));
        }
    }
};

const ReviewListContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(ReviewList);

export default ReviewListContainer;