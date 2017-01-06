/**
 * Created by Yiding Liu on 12/11/2016.
 */
import React from 'react'

class ReviewInfo extends React.Component{
    like(e){
        e.preventDefault();
        //debugger;
        let {index,review,likeReview,userName,dislikeReview}=this.props;
        //debugger;
        if(review.isLiked){
            dislikeReview(review.reviewId,index,userName);
        }
        else{
            likeReview(review.reviewId,index,userName);
        }

    }
    createRatingLabel(){
        let {review}=this.props;
        let overallRating;
        if(review.overallRating<=1){
            overallRating=<span className="label label-danger" style={{marginRight:'5px'}}>{review.overallRating}</span>
        }
        else if(review.overallRating<=4){
            overallRating=<span className="label label-warning" style={{marginRight:'5px'}}>{review.overallRating}</span>
        }
        else{
            overallRating=<span className="label label-success" style={{marginRight:'5px'}}>{review.overallRating}</span>
        }
        return overallRating;
    }
    createLikeBtn(){
        let {review}=this.props;

        if(review.isLiked){
            return(
                <div style={{float:'right',position:'absolute',bottom:'9px',right:'10px'}}>
                    <button type="button" className="btn btn-success btn-sm" onClick={(e)=>{this.like(e);}}>
                        <span className="glyphicon glyphicon-thumbs-up"></span> Like <span className="badge" style={{fontSize:'10px'}}>{review.like}</span>
                    </button>
                </div>
            );
        }
        else{
            return(
                <div style={{float:'right',position:'absolute',bottom:'9px',right:'10px'}}>
                    <button type="button" className="btn btn-primary btn-sm" onClick={(e)=>{this.like(e);}}>
                        <span className="glyphicon glyphicon-thumbs-up"></span> Like <span className="badge" style={{fontSize:'10px'}}>{review.like}</span>
                    </button>
                </div>
            );
        }


    }
    render(){
        //debugger;
        let {review}=this.props;
        let overallRating=this.createRatingLabel();
        let likeBtn=this.createLikeBtn();

        return(
            <div className="list-group-item">
                <h4 className="list-group-item-heading"><strong>{review.reviewTitle}</strong></h4>
                {likeBtn}

                <p className="list-group-item-text" style={{paddingRight:'90px'}}>{review.reviewText}</p>
                <p className="list-group-item-text" >
                    <span className="label label-info" style={{marginRight:'5px'}}>{review.userName}</span>
                    <span className="label label-info" style={{marginRight:'5px'}}>{review.reviewDate}</span>
                    {review.isRecom==true?<span className="label label-success" style={{marginRight:'5px'}}>Recommend</span>:<span className="label label-danger" style={{marginRight:'5px'}}>Not recommend</span>}
                    {overallRating}</p>
            </div>
        )
    }
}

export default ReviewInfo;