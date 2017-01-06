/**
 * Created by Yiding Liu on 12/1/2016.
 */
import React from 'react'
import PagerContainer from '../../containers/PagerContainer'
import ReviewInfo from './ReviewInfo'

class ReviewList extends React.Component{
    componentWillMount(){
        let {count,getReviewList,getNumberOfReviews,setPagerIndex,hotelId,orderBy,userName}=this.props;
        //debugger;
        setPagerIndex(1);
        getReviewList(hotelId,orderBy,1,count,userName);
        getNumberOfReviews(hotelId);
    }
    componentDidUpdate(){
        let {orderBy}=this.props;
        if(this.sortLink!=undefined||this.sortLink!=null){
            let children=this.sortLink.children;
            for(let i=0;i<children.length;i++){
                children[i].className="";
                if(children[i].innerText.trim().toLocaleLowerCase()==orderBy.toLowerCase()){
                    children[i].className="active";
                }
            }
        }
    }
    setOrderBy(e,orderBy){
        e.preventDefault();
        let {setOrderBy,index,count,hotelId,getReviewList,userName}=this.props;
        setOrderBy(orderBy);
        getReviewList(hotelId,orderBy,index,count,userName);
    }
    render(){
        //console.log(this.props.params.hotelId);
        let {fetchingReviewList,reviewList}=this.props;

        if(fetchingReviewList){
            return(
                <div className="list-group container">
                    <h1>LOADING...</h1>
                </div>
            );
        }
        else {
            if(reviewList.length==0){
                return(
                    <div className="list-group container" ref={(div)=>{this.noReviews=div;}}>
                        <h1>No reviews :(</h1>
                    </div>
                );
            }
            else{
                return(
                    <div className="container" style={{width:'100%'}}>
                        <div style={{float:'right',marginTop:'25px'}}>
                            <ul className="nav nav-pills" ref={(sortlink)=>{this.sortLink=sortlink;}}>
                                <li onClick={(e)=>{this.setOrderBy(e,'date');}}><a href="#" style={{padding:'5px 15px 5px 15px'}}>Date</a></li>
                                <li onClick={(e)=>{this.setOrderBy(e,'rating')}}><a href="#" style={{padding:'5px 15px 5px 15px'}}>Rating</a></li>
                            </ul>
                        </div>

                        <PagerContainer updateChild={this.props.getReviewList} type="REVIEW" params={this.props.params}/>
                        <div className="list-group">
                            {reviewList.map((review,i)=>(
                                <ReviewInfo
                                    key={i}
                                    index={i}
                                    review={review}
                                    likeReview={this.props.likeReview}
                                    dislikeReview={this.props.dislikeReview}
                                    userName={this.props.userName}
                                />
                            ))}
                        </div>
                        <PagerContainer updateChild={this.props.getReviewList} type="REVIEW" params={this.props.params}/>
                    </div>
                );
            }

        }
    }
}
export default ReviewList;