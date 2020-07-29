import React, {Component} from 'react';
import Home from './HomeComponent';
import Header from './HeaderComponent';
import About from './AboutUsComponent';
import Menu from './MenuComponent';
import DishDetail from './DishDetailComponent';
import Contact from './ContactComponent';
import Footer from './FooterComponent';
import {Switch, Route, Redirect, withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import { postComment, fetchDishes, fetchComments, fetchPromos, fetchLeaders } from '../redux/ActionCreators';

const mapStateToProps = state =>{
	return{

		dishes: state.dishes,
		comments: state.comments,
		promotions: state.promotions,
		leaders: state.leaders
	};
}

const mapDispatchToProps = dispatch => ({
  
	postComment: (dishId, rating, author, comment) => dispatch(postComment(dishId, rating, author, comment)),
	fetchDishes: () => {dispatch(fetchDishes())},
	fetchComments: () => {dispatch(fetchComments())},
	fetchPromos: () => {dispatch(fetchPromos())},
	fetchLeaders: () => {dispatch(fetchLeaders())}
  });


class Main extends Component{

	componentDidMount(){
		this.props.fetchDishes();
		this.props.fetchComments();
		this.props.fetchPromos();
		this.props.fetchLeaders();
	}

	render(){
		
    const HomePage = () => {
      return(
		  <Home 
		  	  dish={this.props.dishes.dishes.filter((dish) => dish.featured)[0]}
			  dishesErrMess ={this.props.dishes.errMess}
			  dishesLoading={this.props.dishes.isLoading}
			  promotion={this.props.promotions.promotions.filter((promo) => promo.featured)[0]}
			  promosErrMess ={this.props.promotions.errMess}
			  promosLoading={this.props.promotions.isLoading}
			  leader={this.props.leaders.leaders.filter((leader) => leader.featured)[0]}
			  leadersErrMess ={this.props.leaders.errMess}
			  leadersLoading={this.props.leaders.isLoading}
          />
      );
    }

	const DishDetails = ({match}) => {
		
		return(
			<DishDetail
				isLoading={this.props.dishes.isLoading}
				dishesErrMess ={this.props.dishes.errMess}
				selectedDish={this.props.dishes.dishes.filter((dish) => dish.id === parseInt(match.params.dishId,10))[0]}
				comments={this.props.comments.comments.filter((comment) => comment.dishId=== parseInt(match.params.dishId,10))}
				commentsErrMess ={this.props.comments.errMess}
				postComment={this.props.postComment}
			/> 
		);
	}
	
	const AboutUs = () => {
		
		return(
			<About leaders={this.props.leaders.leaders} />
		)
	}
		
	return (
		  <div>
			  <Header />
				<Switch>
					<Route path="/home" component={HomePage}/>
					<Route exact path="/aboutus" component={AboutUs} />
					<Route exact path="/menu" component={() => <Menu dishes={this.props.dishes} />} />
					<Route path="/menu/:dishId" component={DishDetails} />
					<Route exact path="/contactus" component={Contact} />
					<Redirect to="/home" />
				</Switch>
			  <Footer />
          </div>
	   );
	}
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Main));
