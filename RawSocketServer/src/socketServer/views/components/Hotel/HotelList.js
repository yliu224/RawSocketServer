/**
 * Created by Yiding Liu on 12/1/2016.
 */
import React from 'react'
import HotelInfo from './HotelInfo'
import PagerContainer from '../../containers/PagerContainer'

class HotelList extends React.Component{
    componentWillMount(){
        let {count,getNumberOfHotelsByName,getHotelListByName,setHotelNameFilter,setPagerIndex}=this.props;
        setPagerIndex(1);
        getHotelListByName("",1,count);
        getNumberOfHotelsByName("");
        setHotelNameFilter("");
    }
    search(e){
        e.preventDefault();
        let {count,getHotelListByName,getNumberOfHotelsByName,setPagerIndex,setHotelNameFilter}=this.props;
        let name=this.searchText.value;
        setPagerIndex(1);
        getHotelListByName(name,1,count);
        getNumberOfHotelsByName(name);
        setHotelNameFilter(name);
    }
    render(){
        let {hotelList,fetching,firstLoad} =this.props;
        if(fetching&&firstLoad){
            return (
                <div className="list-group list-container container">
                    <h1>LOADING...</h1>
                </div>
            );
        }
        else{
            if(hotelList.length==0){
                return (
                    <div className="container">
                        <div className="jumbotron list-container text-center"><h1>Hotel List</h1></div>
                        <form style={{float:'right',width:'250px',position:'relative',top:'20px'}}>
                            <div className="input-group">
                                <input type="text" className="form-control" placeholder="Search" ref={(input)=>{this.searchText=input;}}/>
                                <div className="input-group-btn">
                                    <button className="btn btn-default" type="submit" onClick={(e)=>{this.search(e);}}><i className="glyphicon glyphicon-search"></i></button>
                                </div>
                            </div>
                        </form>
                        <PagerContainer updateChild={this.props.getHotelListByName} type="HOTEL"/>
                        <div className="list-group">
                            <h1>No reviews found :(</h1>
                        </div>
                        <PagerContainer updateChild={this.props.getHotelListByName} type="HOTEL"/>
                    </div>
                );
            }
            else{
                //debugger;
                return(
                    <div className="container">
                        <div className="jumbotron list-container text-center"><h1>Hotel List</h1></div>
                        <form style={{float:'right',width:'250px',position:'relative',top:'20px'}}>
                            <div className="input-group">
                                <input type="text" className="form-control" placeholder="Search" ref={(input)=>{this.searchText=input;}}/>
                                <div className="input-group-btn">
                                    <button className="btn btn-default" type="submit" onClick={(e)=>{this.search(e);}}><i className="glyphicon glyphicon-search"></i></button>
                                </div>
                            </div>
                        </form>
                        <PagerContainer updateChild={this.props.getHotelListByName} type="HOTEL"/>
                        <div className="list-group">
                            {hotelList.map((hotel)=>(
                                <HotelInfo key={hotel.hotelId} hotel={hotel}/>
                            ))}
                        </div>
                        <PagerContainer updateChild={this.props.getHotelListByName} type="HOTEL"/>
                    </div>
                );
            }
        }
    }
};
export default HotelList;