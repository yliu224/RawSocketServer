/**
 * Created by Yiding Liu on 11/28/2016.
 */
const visibilityFilter=(state='SHOW_ALL', action)=>{
    switch (action.type){
        case 'SET_VISIBILITY_FILTER':
            return action.filter
        default:
            return state
    }
}
export default visibilityFilter