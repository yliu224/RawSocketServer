/**
 * Created by Yiding Liu on 11/28/2016.
 */
import React,{PropTypes} from 'react'
import Todo from './Todo'

class TodoList extends React.Component{
    render(){
        let {todos,onTodoClick}=this.props;
        return(
            <ul>
                {
                    todos.map(todo=>
                        <Todo onClick={()=>onTodoClick(todo.id)} key={todo.id} completed={todo.completed} text={todo.text} />
                    )
                }
            </ul>
        );
    }
}

TodoList.propTypes = {
    todos: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.number.isRequired,
        completed: PropTypes.bool.isRequired,
        text: PropTypes.string.isRequired
    }).isRequired).isRequired,
    onTodoClick: PropTypes.func.isRequired
}

export default TodoList