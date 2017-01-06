/**
 * Created by Yiding Liu on 12/11/2016.
 */
import React from 'react'

class AddOrEditReviewInfo extends React.Component{
    componentWillMount(){
        let {getReview,hotelId,userName}=this.props;
        getReview(hotelId,userName);
    }
    componentDidUpdate(){
        let {review,fetchingReview}=this.props;
        if(review==null||review==undefined){
            this.title.value="";
            this.text.value="";
            this.isRecom.checked=false;
            let children=this.rating.children;
            //debugger;
            for(let i=1;i<children.length;i++){
                children[i].children[0].checked=false;
            }
        }
        if(!fetchingReview&&review!=null||review!=undefined){
            this.title.value=review.reviewTitle;
            this.text.value=review.reviewText;
            this.isRecom.checked=review.isRecom;
            let children=this.rating.children;
            //debugger;
            for(let i=1;i<children.length;i++){
                if(children[i].innerText==review.overallRating){
                    children[i].children[0].checked=true;
                }
            }
        }
    }
    submitReview(e,type){
        e.preventDefault();
        let {addReview,editReview,hotelId,review,userName,index,count,orderBy}=this.props;
        let reviewData={};
        let children=this.rating.children;
        let rating=0;
        for(let i=1;i<children.length;i++){
            if(children[i].children[0].checked){
                rating=children[i].innerText;
            }
        }
        //debugger;
        switch(type){
            case "AddReview":
                reviewData={
                    reviewId:"",
                    hotelId:hotelId,
                    reviewText:this.text.value,
                    reviewTitle:this.title.value,
                    userName:userName,
                    overallRating:rating,
                    isRecom:this.isRecom.checked
                };
                addReview(reviewData,orderBy,index,count,userName);
                break;
            case "EditReview":
                reviewData={
                    reviewId:review.reviewId,
                    hotelId:hotelId,
                    reviewText:this.text.value,
                    reviewTitle:this.title.value,
                    userName:userName,
                    overallRating:rating,
                    isRecom:this.isRecom.checked
                };
                editReview(reviewData,orderBy,index,count,userName);
                break;
        }
        this.close.click();
    }
    render(){
        let {review,fetchingReview}=this.props;
        let type='AddReview';
        if(!fetchingReview&&review!=null||review!=undefined){
            // this.title.value=review.reviewTitle;
            // this.text.value=review.reviewText;
            // this.isRecom.checked=review.isRecom;
            // let children=this.rating.children;
            // //debugger;
            // for(let i=1;i<children.length;i++){
            //     if(children[i].innerText==review.overallRating){
            //         children[i].children[0].checked=true;
            //     }
            // }
            type="EditReview";
        }

        return(
            <div className="modal fade" id="reviewModal" role="dialog">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal" ref={(btn)=>{this.close=btn;}}>x</button>
                            <h4 className="text-center"><strong>My Review</strong></h4>
                        </div>
                        <div className="modal-body">
                            <form action="" role="form">
                                <div className="form-group">
                                    <label htmlFor="title">Title</label>
                                    <input type="text" className="form-control" id="title" placeholder="Title here!" ref={(input)=>{this.title=input;}}/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="text">Text</label>
                                    <textarea style={{height:'100px'}} type="text" className="form-control" id="text" placeholder="Text here!" ref={(input)=>{this.text=input;}}/>
                                </div>
                                <div className="form-group">
                                    <div id="rating" className="col-sm-8" style={{paddingLeft:'0px'}} ref={input=>{this.rating=input;}}>
                                        <label style={{marginRight:'10px'}}>Rating</label>
                                        <label className="radio-inline">
                                            <input type="radio" name="optradio"/> 1
                                        </label>
                                        <label className="radio-inline">
                                            <input type="radio" name="optradio"/> 2
                                        </label>
                                        <label className="radio-inline">
                                            <input type="radio" name="optradio"/> 3
                                        </label>
                                        <label className="radio-inline">
                                            <input type="radio" name="optradio"/> 4
                                        </label>
                                        <label className="radio-inline">
                                            <input type="radio" name="optradio"/> 5
                                        </label>
                                    </div>
                                    <div id="isRecom" className="col-sm-4">
                                        <label className="checkbox-inline" style={{float:'right'}}>
                                            <input type="checkbox" ref={(input)=>{this.isRecom=input;}}/> Recommend
                                        </label>
                                    </div>
                                </div>
                                <div>
                                    <button type="submit" className="btn btn-block btn-primary" onClick={(e)=>{this.submitReview(e,type)}}>Submit<span className="glyphicon glyphicon-ok"></span>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default AddOrEditReviewInfo;