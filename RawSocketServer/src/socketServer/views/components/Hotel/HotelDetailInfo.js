/**
 * Created by Yiding Liu on 12/10/2016.
 */
import React from 'react'
import HotelDetailTabs from './HotelDetailTabs'
import AddOrEditReviewButtonContainer from '../../containers/AddOrEditReviewButtonContainer'
import AddOrEditReviewInfoContainer from '../../containers/AddOrEditReviewInfoContainer'

class HotelDetailInfo extends React.Component{
    componentWillMount(){
        let {getHotelDetail} = this.props;
        getHotelDetail(this.props.params.hotelId);

    }
    createRatingBar(){
        let {hotel}=this.props;
        let style,className='progress-bar';
        let width=(hotel.rating/5*100)+'%';
        style={width:width};
        //debugger;

        if(hotel.rating<=1){
            className+=' progress-bar-danger';
        }
        else if(hotel.rating<4){
            className+=' progress-bar-warning';
        }
        else{
            className+=' progress-bar-success';
        }
        return(
            <div className="progress" style={{width:'160px',height:"32px"}}>
                <img src="/images/default/star.png" alt="Rating" style={{width:'32px',heigth:'32px',float:'left',position:'absolute',left:'15px'}}/>
                <img src="/images/default/star.png" alt="Rating" style={{width:'32px',heigth:'32px',float:'left',position:'absolute',left:'47px'}}/>
                <img src="/images/default/star.png" alt="Rating" style={{width:'32px',heigth:'32px',float:'left',position:'absolute',left:'79px'}}/>
                <img src="/images/default/star.png" alt="Rating" style={{width:'32px',heigth:'32px',float:'left',position:'absolute',left:'111px'}}/>
                <img src="/images/default/star.png" alt="Rating" style={{width:'32px',heigth:'32px',float:'left',position:'absolute',left:'143px'}}/>
                <div className={className} role="progressbar" aria-valuenow={100} aria-valuemin={0} aria-valuemax={100} style={style} />
            </div>
        );
    }
    render(){
        //debugger;
        let {hotel} = this.props;

        if(hotel==null||hotel==undefined){
            return(
                <div className="list-group list-container container">
                    <h1>LOADING...</h1>
                </div>
            );
        }
        else{
            let ratingBar=this.createRatingBar();
            //debugger;
            return (
                <div>
                <div className="container">
                    <div className="row">
                        <div className="col-lg-12">
                            <h1 className="page-header">{hotel.name}</h1>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-8">
                            <img className="img-responsive" src={hotel.imgUrl} alt=""/>
                        </div>

                        <div className="col-md-4">
                            <h3>Address</h3>
                            <p><span className="glyphicon glyphicon-map-marker"></span>{hotel.state},{hotel.city},{hotel.addr}</p>
                            <p><span className="glyphicon glyphicon-phone"></span>Phone: +00 1515151515</p>
                            <p><span className="glyphicon glyphicon-envelope"></span>Email: mail@mail.com</p>
                            <br/>
                            <h3>Rating</h3>
                            <h3>{parseFloat(hotel.rating).toFixed(1)}</h3>
                            {ratingBar}
                            <br/>
                            <AddOrEditReviewButtonContainer/>
                            <AddOrEditReviewInfoContainer params={this.props.params}/>
                        </div>
                    </div>
                </div>
                <HotelDetailTabs selectedTab={this.props.selectedTab} setTab={this.props.setTab} params={this.props.params}/>
                </div>
            );
        }
    }
}
export default HotelDetailInfo