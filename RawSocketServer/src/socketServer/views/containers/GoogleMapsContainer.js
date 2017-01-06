/**
 * Created by Yiding Liu on 12/12/2016.
 */
import GoogleMaps from '../components/GoogleMaps/GoogleMaps'
import {connect} from 'react-redux'

const mapStateToProps= (state) =>{
    //index:state.pagerData.index,
    //debugger;
    return{
        hotel:state.hotelData.hotel
    }
};
const mapDispatchToProps = dispatch =>{
    return {}
};

const GoogleMapsContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(GoogleMaps);

export default GoogleMapsContainer;