/**
 * Created by Yiding Liu on 12/1/2016.
 */
import React from 'react'
import {Link} from 'react-router'

class HotelInfo extends React.Component{
    render(){
        let {hotel}=this.props;
        let expediaHotelAddress="https://www.expedia.com/h"+hotel.hotelId+".Hotel-Information";
        let hotelLink="/Review/"+hotel.hotelId;
        let borderLeft;

        let overallRating;
        if(hotel.rating<=1){
            overallRating=<span className="label label-danger" style={{marginRight:'5px'}}>Overall Rating: {parseFloat(hotel.rating).toFixed(1)}</span>
            borderLeft={borderLeft:'5px solid #d9534f'};
        }
        else if(hotel.rating<4){
            overallRating=<span className="label label-warning" style={{marginRight:'5px'}}>Overall Rating: {parseFloat(hotel.rating).toFixed(1)}</span>
            borderLeft={borderLeft:'5px solid #f0ad4e'};
        }
        else{
            overallRating=<span className="label label-success" style={{marginRight:'5px'}}>Overall Rating: {parseFloat(hotel.rating).toFixed(1)}</span>
            borderLeft={borderLeft:'5px solid #5cb85c'};
        }

        return(
            <div className="list-group-item" style={borderLeft}>
                <h4 className="list-group-item-heading">
                    <Link to={hotelLink} style={{color:'black'}}>{hotel.name}</Link>
                    <a href={expediaHotelAddress} style={{fontSize:'12px'}}>Expedia Link</a>
                </h4>
                <p className="list-group-item-text text-left" style={{float:'left'}}>{hotel.state},{hotel.city},{hotel.addr}</p>
                <p className="list-group-item-text text-right">{overallRating}</p>
            </div>

        );
    }
}
export default HotelInfo;