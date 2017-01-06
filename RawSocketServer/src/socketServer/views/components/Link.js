/**
 * Created by Yiding Liu on 11/28/2016.
 */
import React, { PropTypes } from 'react'

class Link extends React.Component{
    render(){
        let {active,onClick,children}=this.props;
        if(this.props.active){
            return(
                <span>{children}</span>
            );
        }
        return (
            <a href="#"
               onClick={e => {
                   e.preventDefault();
                   onClick();
               }}
            >
                {children}
            </a>
        );
    }
}
Link.propTypes = {
    active: PropTypes.bool.isRequired,
    children: PropTypes.node.isRequired,
    onClick: PropTypes.func.isRequired
}

export default Link