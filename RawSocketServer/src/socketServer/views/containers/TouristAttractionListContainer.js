/**
 * Created by Yiding Liu on 12/12/2016.
 */
import TouristAttractionList from '../components/TouristAttraction/TouristAttractionList'
import {connect} from 'react-redux'
import {getTouristAttraction} from '../actions/touristAttractionActions'

const mapStateToProps= (state) =>{
    //index:state.pagerData.index,
    //debugger;
    return{
        touristAttractions:state.touristAttractionData.touristAttractions,
        hotel:state.hotelData.hotel
    }
};
const mapDispatchToProps = dispatch =>{
    return {
        getTouristAttraction:(lat,lng,radius,city)=>{
            dispatch(getTouristAttraction(lat,lng,radius,city));
        }
    }
};

const TouristAttractionListContainer=connect(
    mapStateToProps,
    mapDispatchToProps
)(TouristAttractionList);

export default TouristAttractionListContainer;