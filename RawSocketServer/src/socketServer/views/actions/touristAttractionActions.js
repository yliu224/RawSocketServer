/**
 * Created by Yiding Liu on 12/12/2016.
 */
import {GET_TOURIST_ATTRACTION,SET_TOURIST_ATTRACTION}from './actionConstant'
import axios from 'axios';

//API call
export const getTouristAttraction=(lat,lng,radius,city)=>{
    return dispatch=>{
        // //debugger;
        // axios.get('https://maps.googleapis.com/maps/api/place/nearbysearch/json?' +
        //     'location='+lat+','+lng+'&radius='+radius*1609+'&query=tourist attraction+in+'+city+'&key=AIzaSyAvaCyjuJXQnjoskpcetqEUtr13YReoBQ4',{})
        //     .then(response=>{
        //         if(response.data.status=="OK"){
        //             dispatch(setTouristAttraction(response.data.results));
        //         }
        //         else {
        //             console.log(response.data);
        //         }
        //     })
        //     .catch(err=>{
        //         console.log(err);
        //     });

        let pyrmont = new google.maps.LatLng(lat,lng);

        let map = new google.maps.Map(document.getElementById('map'), {
            center: pyrmont,
            zoom: 15
        });

        let request = {
            location: pyrmont,
            radius: radius*1609,
            query: 'tourist attraction+in+'+city
        };

        let service = new google.maps.places.PlacesService(map);
        service.textSearch(request, (results,status)=>{
            //debugger;
            if(status=="OK"){
                dispatch(setTouristAttraction(results));
            }
            else{
                console.log(status);
            }
        });
    }
};
//Internal call
export const setTouristAttraction=(results)=>{
    return{
        type:SET_TOURIST_ATTRACTION,
        results
    }
};