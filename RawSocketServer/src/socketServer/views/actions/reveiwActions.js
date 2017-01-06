/**
 * Created by Yiding Liu on 12/11/2016.
 */
import {GET_REVIEW_LIST,GET_REVIEW_LIST_FAILED,GET_REVIEW_LIST_SUCCESS,
    GET_NUMBER_OF_REVIEWS,GET_NUMBER_OF_REVIEWS_FAILED,GET_NUMBER_OF_REVIEWS_SUCCESS,
    SET_REViEW_LIST,SET_REVIEW,SET_LIKE_REVIEW,SET_ORDER_BY,SET_DISLIKE_REVIEW,
    ADD_REVIEW,ADD_REVIEW_FAILED,EDIT_REVIEW,EDIT_REVIEW_FAILED,GET_REIVEW,GET_REVIEW_FAILED
} from './actionConstant'
import {setTotal} from './pagerActions'
import {getHotelRating} from './hotelActions'
import axios from 'axios';

//API call
export const getReviewList=(hotelId,orderBy,index,count,userName)=>{
    return dispatch=>{
        axios.get('/Review/getReviewList?hotelId='+hotelId+'&orderBy='+orderBy+'&index='+index+'&count='+count+'&userName='+userName,{})
            .then(response=>{
               if(response.data.success==true){
                   dispatch(setReviewList(response.data.data));
               }
               else {
                   dispatch(getReviewListSuccess(response));
               }
            })
            .catch(err=>{
                dispatch(getReviewListFailed(err))
            });
    }
};
export const getNumberOfReviews=(hotelId)=>{
    return dispatch=>{
        axios.get('/Review/getReviewNumbers?hotelId='+hotelId,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setTotal(response.data.message));
                }
                else {
                    dispatch(getNumberOfReviewsSuccess(response));
                }
            })
            .catch(err=>{
                dispatch(getNumberOfReviewsFailed(err));
            });
    }
};
export const getReview=(hotelId,userName)=>{
    return dispatch=>{
        axios.get('/Review/getReview?userName='+userName+'&hotelId='+hotelId,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setReview(response.data.data));
                }
                else{
                    dispatch(setReview(null));
                }
            })
            .catch(err=>{
                console.log(err);
            })
    }
};
export const addReview=(reviewJson,orderBy,index,count,userName)=>{
    return dispatch=>{
        axios.post('/Review/addReview',reviewJson)
            .then(response=>{
                if(response.data.success==true){
                    //debugger;
                    let review=response.data.data;
                    dispatch(getReviewList(review.hotelId,orderBy,index,count,userName));
                    dispatch(getNumberOfReviews(review.hotelId));
                    dispatch(setReview(review));
                    dispatch(getHotelRating(review.hotelId));
                }
            })
            .catch(err=>{
                console.log(err);
            });
    }
};
export const editReview=(reviewJson,orderBy,index,count,userName)=>{
    return dispatch=>{
        axios.post('/Review/editReview',reviewJson)
            .then(response=>{
                if(response.data.success==true){
                    let review=response.data.data;
                    dispatch(getReviewList(review.hotelId,orderBy,index,count,userName));
                    dispatch(setReview(review));
                    dispatch(getHotelRating(review.hotelId));
                }
            })
            .catch(err=>{
                console.log(err);
            });
    }
};
export const likeReview=(reviewId,index,userName)=>{
    return dispatch=>{
        axios.get('/Review/likeReview?reviewId='+reviewId+'&userName='+userName,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setLikeReview(index));
                }
                else {
                    alert("You've already liked the review!")
                }
            })
            .catch(err=>{
                console.log(err);
            })
    }
};
export const deleteReview=(reviewId,orderBy,index,count,hotel,userName)=>{
    return dispatch=>{
        axios.get('/Review/deleteReview?reviewId='+reviewId,{})
            .then(response=>{
                if(response.data.success==true){
                    //debugger;
                    dispatch(getReviewList(hotel.hotelId,orderBy,index,count,userName));
                    dispatch(setReview(null));
                    dispatch(getHotelRating(hotel.hotelId));
                }
                else {
                    //debugger;
                    console.log(response);
                }
            })
            .catch(err=>{
                console.log(err);
            })
    }
};
export const disLikeReview=(reviewId,index,userName)=>{
    return dispatch=>{
        axios.get('/Review/disLikeReview?reviewId='+reviewId+'&userName='+userName,{})
            .then(response=>{
                if(response.data.success==true){
                    dispatch(setDislikeReview(index));
                }
                else {
                    alert("You've already disliked the review!")
                }
            })
            .catch(err=>{
                console.log(err);
            })
    }
};

//Internal call
export const getReviewListSuccess=payload=>{
    return{
        type:GET_REVIEW_LIST_SUCCESS,
        payload
    }
};
export const getReviewListFailed=payload=>{
    return{
        type:GET_REVIEW_LIST_FAILED,
        payload
    }
};
export const getNumberOfReviewsSuccess=payload=>{
    return {
        type:GET_NUMBER_OF_REVIEWS_SUCCESS,
        payload
    }
};
export const getNumberOfReviewsFailed=payload=>{
    return{
        type:GET_NUMBER_OF_REVIEWS_FAILED,
        payload
    }
};
export const setReviewList=reviewList=>{
    return{
        type:SET_REViEW_LIST,
        reviewList
    }
};
export const setReview=review=>{
    return{
        type:SET_REVIEW,
        review
    }
};
export const setLikeReview=(index)=>{
    return{
        type:SET_LIKE_REVIEW,
        index
    }
};
export const setDislikeReview=(index)=>{
    return{
        type:SET_DISLIKE_REVIEW,
        index
    }
};
export const setOrderBy=(orderBy)=>{
    return{
        type:SET_ORDER_BY,
        orderBy
    }
};