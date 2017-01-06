/**
 * Created by Yiding Liu on 11/28/2016.
 */
import React from 'react'
import RouteFilterLink from '../containers/RouteFilterLink'

class Footer extends React.Component{
    render(){
        return(
            <p>
                Show:
                {" "}
                <RouteFilterLink filter="all">
                    All
                </RouteFilterLink>
                {", "}
                <RouteFilterLink filter="active">
                    Active
                </RouteFilterLink>
                {", "}
                <RouteFilterLink filter="completed">
                    Completed
                </RouteFilterLink>
            </p>
        );
    }
}

export default Footer