/**
 * Created by Yiding Liu on 12/12/2016.
 */
import React from 'react'
import TouristAttractionInfo from './TouristAttractionInfo'

class TouristAttractionList extends React.Component{
    componentWillMount(){
        let {hotel,getTouristAttraction}=this.props;
        getTouristAttraction(hotel.lat,hotel.lng,10,hotel.city);
    }
    render(){
        //console.log(this.props.params.hotelId);

        let {touristAttractions}=this.props;
        //debugger;
        if(touristAttractions.length==0){
            return(
                <div className="list-group list-container container">
                    <h1>LOADING...</h1>
                </div>
            );
        }
        else {
            return(
                <div className="container" style={{width:'100%',marginTop:'50px'}}>
                    <div className="list-group">
                        {touristAttractions.map((attraction)=>(
                            <TouristAttractionInfo key={attraction.id} attraction={attraction}/>
                        ))}
                    </div>
                </div>
            );
        }
    }
}
export default TouristAttractionList;