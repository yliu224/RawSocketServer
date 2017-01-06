/**
 * Created by Yiding Liu on 12/10/2016.
 */
import React from 'react'
import ReviewListContainer from '../../containers/ReviewListContainer'
import TouristAttractionContainer from '../../containers/TouristAttractionListContainer'
import GoogleMapsContainer from '../../containers/GoogleMapsContainer'

class HotelDetailTabs extends React.Component{
    componentDidMount(){
        this.setActiveTab()
    }
    componentDidUpdate(){
        this.setActiveTab()
    }
    setActiveTab(){
        let children=this.list.childNodes;
        let {selectedTab} = this.props;
        children.forEach((item)=>{
            if(item.textContent.toLocaleLowerCase()==selectedTab){
                item.className="active";
            }
            else{
                item.className="";
            }
        });
    }
    setTabContent(){
        let {selectedTab}=this.props;
        //debugger;
        switch (selectedTab){
            case "reviews":
                this.tabContent=<ReviewListContainer params={this.props.params}/>;
                break;
            case "tourist attraction":
                this.tabContent=<TouristAttractionContainer />;
                break;
            case "google maps":
                this.tabContent=<GoogleMapsContainer/>;
                break;
            default:
                this.tabContent=<ReviewListContainer params={this.props.params}/>;
        }
    }
    changeTab(e,tab){
        //debugger;
        e.preventDefault();
        this.props.setTab(tab);
    }
    render(){
        this.setTabContent();
        return(
        <div className="container" style={{marginTop:'30px'}}>
            <ul className="nav nav-tabs" ref={(ul)=>{this.list=ul;}}>
                <li onClick={(e)=>{this.changeTab(e,'reviews')}} key="reviews"><a href="#">Reviews</a></li>
                <li onClick={(e)=>{this.changeTab(e,'tourist attraction')}} key="tourist attraction"><a href="#">Tourist Attraction</a></li>
                <li onClick={(e)=>{this.changeTab(e,'google maps')}} key="google maps"><a href="#">Google Maps</a></li>
            </ul>
            {this.tabContent}
        </div>
        );
    }
}
export default HotelDetailTabs;