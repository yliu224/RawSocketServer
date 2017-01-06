/**
 * Created by Yiding Liu on 11/28/2016.
 */
import React,{PropTypes} from 'react'


class Todo extends React.Component{
    render(){
        let {onClick,completed,text}=this.props;
        return(
            <li
                onClick={onClick}
                style={{textDecoration:completed?'line-through':'none'}}
            >
                {text}
            </li>
        );
    }
}

//Verification
Todo.propTypes={
    onClick:PropTypes.func.isRequired,
    completed:PropTypes.bool.isRequired,
    text:PropTypes.string.isRequired
}

export default Todo