/**
 * Created by Yiding Liu on 12/2/2016.
 */
import React from 'react'

class Pagers extends React.Component{
    constructor(props){
        super(props);
        this.setIndex=this.setIndex.bind(this);
    }
    setIndex(e,i){
        let {count,setIndex,updateChild,type,params,orderBy,hotelNameFilter,userName} =this.props;
        e.preventDefault();
        setIndex(i);
        switch(type){
            case "HOTEL":
                updateChild(hotelNameFilter,i,count);
                break;
            case "REVIEW":
                updateChild(params.hotelId,orderBy,i,count,userName);
                break;
        }

    }
    render(){

        let {index,total,count,children}=this.props;
        let cnt=Math.ceil(total/count);
        let pagers=[];
        for(var i=1;i<=cnt;i++){
            pagers.push(i);
        }
        let elems=pagers.map(i=>{
            if(i==index){
                return (<li key={i} className="active" ><a href="#">{i}</a></li>);
            }
            else {
                return (<li key={i} onClick={(e)=>this.setIndex(e,i)}><a href="#">{i}</a></li>);
            }
        });
        return(
            <div>
                <ul className="pagination">
                    {elems}
                </ul>
            </div>
        );
    }
}
export default Pagers;