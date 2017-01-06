/**
 * Created by Yiding Liu on 12/12/2016.
 */
import React from 'react'

class TouristAttractionInfo extends React.Component{
    render(){
        //debugger;
        let {attraction}=this.props;
        return(
            <div className="list-group-item">
                <h4 className="list-group-item-heading"><strong>{attraction.name}</strong></h4>
                <p className="list-group-item-text">Rating : {attraction.rating}</p>
            </div>
        )
    }
}

export default TouristAttractionInfo;