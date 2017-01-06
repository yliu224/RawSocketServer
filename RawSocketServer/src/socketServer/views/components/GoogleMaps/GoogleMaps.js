/**
 * Created by Yiding Liu on 12/12/2016.
 */
import React from 'react'

class GoogleMaps extends React.Component{
    render(){
        let {hotel}=this.props;
        hotel.name=hotel.name.replace('&','');

        let src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCtvvex8V05U-XvIMHeoOmtL2iH0Ifw7Wg&q="+hotel.name+"+"+hotel.city+"+"+hotel.state;
        return(
            <div style={{marginTop:'50px',marginBottom:'50px'}}>
                <iframe width="100%" height={550} frameBorder={0} style={{border: 0,borderRadius:'5px'}} src={src} allowFullScreen>
                </iframe>
            </div>
        );
    }
}
export default GoogleMaps