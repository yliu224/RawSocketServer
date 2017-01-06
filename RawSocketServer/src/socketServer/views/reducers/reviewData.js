/**
 * Created by Yiding Liu on 12/11/2016.
 */
import {GET_REVIEW_LIST,GET_REVIEW_LIST_FAILED,GET_REVIEW_LIST_SUCCESS,
        GET_NUMBER_OF_REVIEWS,GET_NUMBER_OF_REVIEWS_FAILED,GET_NUMBER_OF_REVIEWS_SUCCESS,
        SET_REViEW_LIST,SET_REVIEW,SET_LIKE_REVIEW,SET_ORDER_BY,SET_DISLIKE_REVIEW,
        ADD_REVIEW,ADD_REVIEW_FAILED,EDIT_REVIEW,EDIT_REVIEW_FAILED,GET_REIVEW,GET_REVIEW_FAILED
} from '../actions/actionConstant'
let initialState={
    fetchingReview:true,
    fetchingReviewList:true,
    response:null,
    reviewList:[],
    orderBy:'date',
    review:null
};
const reviewData=(state=initialState ,action)=>{
    switch(action.type){
        case GET_REVIEW_LIST_SUCCESS:
            return {...state,response:action.payload};
        case GET_REVIEW_LIST_FAILED:
            return {...state,response:action.payload};
        case GET_NUMBER_OF_REVIEWS_SUCCESS:
            return {...state,response:action.payload};
        case GET_NUMBER_OF_REVIEWS_FAILED:
            return {...state,response:action.payload};
        case SET_REViEW_LIST:
            return {...state,fetchingReviewList:false,reviewList:action.reviewList};
        case SET_REVIEW:
            return {...state,fetchingReview:false,review:action.review};
        case ADD_REVIEW_FAILED:
            return {...state,response:action.payload};
        case EDIT_REVIEW_FAILED:
            return {...state,response:action.payload};
        case GET_REVIEW_FAILED:
            return {...state,response:action.payload};
        case SET_LIKE_REVIEW:
            let newReviewList_like=[];
            for(let i=0;i<state.reviewList.length;i++){
                if(i==action.index){
                    state.reviewList[i].like+=1;
                    state.reviewList[i].isLiked=true;
                }
                newReviewList_like.push(state.reviewList[i]);
            }
            return {...state,reviewList:newReviewList_like};
        case SET_DISLIKE_REVIEW:
            let newReviewList_dislike=[];
            for(let i=0;i<state.reviewList.length;i++){
                if(i==action.index){
                    state.reviewList[i].like-=1;
                    state.reviewList[i].isLiked=false;
                }
                newReviewList_dislike.push(state.reviewList[i]);
            }
            return {...state,reviewList:newReviewList_dislike};
        case SET_ORDER_BY:
            return {...state,orderBy:action.orderBy};
        default:
            return state;
    }
};
export default reviewData