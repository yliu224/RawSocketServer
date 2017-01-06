/**
 * Created by Yiding Liu on 12/11/2016.
 */
import React from 'react'

class AddOrEditReviewButton extends React.Component{
    componentDidUpdate(){
        let {review,fetchingReview}=this.props;
        //debugger;
        if(!fetchingReview){
            if(review==null||review==undefined) {
                this.add.classList.remove('disabled');
            }
            else{
                this.edit.classList.remove('disabled');
                this.delete.classList.remove('disabled');
            }
        }
    }
    deleteReview(e){
        e.preventDefault();
        let {deleteReview,review,pagerInfo,orderBy,hotel}=this.props;
        if (confirm("Are you sure to delete the review ?") == true) {
            deleteReview(review.reviewId,orderBy,pagerInfo.index,pagerInfo.count,hotel);
        }
    }
    render(){
        let {review,fetchingReview}=this.props;
        //debugger;
        if(fetchingReview){
            return(<div></div>);
        }
        else{
            if(review==null||review==undefined){
                return(
                    <button className="btn btn-success disabled" data-toggle="modal" data-target="#reviewModal" ref={(btn)=>{this.add=btn;}}>Add Review</button>
                );
            }
            else{
                return(
                    <div>
                        <button className="btn btn-primary disabled" data-toggle="modal" data-target="#reviewModal" ref={(btn)=>{this.edit=btn}}>Edit Review</button>
                        <button style={{marginLeft:'5px'}} className="btn btn-danger disabled" ref={(btn)=>{this.delete=btn;}} onClick={(e)=>{this.deleteReview(e);}}>Delete Review</button>
                    </div>
                );
            }
        }

    }
}
export default AddOrEditReviewButton;