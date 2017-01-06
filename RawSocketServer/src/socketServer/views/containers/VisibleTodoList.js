/**
 * Created by Yiding Liu on 11/28/2016.
 */
import { connect } from 'react-redux'
import { toggleTodo } from '../actions'
import TodoList from '../components/TodoList'

//It will return a list of todos,which have already been filtered
const getVisibleTodos = (todos, filter) => {
    switch (filter) {
        case 'all':
            return todos;
        case 'completed':
            return todos.filter(t => t.completed)//filter is something like map(()=>{});
        case 'active':
            return todos.filter(t => !t.completed)
    }
}

const mapStateToProps = (state,props) => {
    return {
        todos: getVisibleTodos(state.todos, props.filter)
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onTodoClick: (id) => {
            dispatch(toggleTodo(id))
        }
    }
}

const VisibleTodoList = connect(
    mapStateToProps,
    mapDispatchToProps
)(TodoList)

export default VisibleTodoList