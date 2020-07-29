import React, {Component} from 'react';
import {Navbar,NavbarBrand, Nav, NavbarToggler, Collapse, NavItem, 
	Jumbotron,Modal, ModalHeader, ModalBody, Button,
	Form, FormGroup, Label, Input} from 'reactstrap';
import {NavLink} from 'react-router-dom';

class Header extends Component{
	
	constructor(props){
		
		super(props);
		this.state = {
			isNavOpen : false,
			isModalOpen:false
		};
		this.toggleNav = this.toggleNav.bind(this);
		this.toggleModal = this.toggleModal.bind(this);
		this.handleLogin = this.handleLogin.bind(this);
		
	}
	
	toggleNav(){

		this.setState(
			{
				isNavOpen:!this.state.isNavOpen
			}		
		);
	}

	toggleModal(){

		this.setState(
			{
				isModalOpen:!this.state.isModalOpen
			}		
		);
	}

	handleLogin(event){
		
		this.toggleModal();
		alert("username:"+ this.username.value+ " password:"+ this.password.value + " remember:"+this.remember.checked);
	}

	render(){
			
		return (
			<>
				<Navbar dark color="primary" expand="md" className="fixed-top">
					<div className="container">
						<NavbarToggler onClick={this.toggleNav} />
						<NavbarBrand className="mr-auto" href="/">
						<img alt="" src="assets/images/logo.png" height="30" width="41" />
						</NavbarBrand>
						<Collapse isOpen={this.state.isNavOpen} navbar>
						<Nav navbar>
							<NavItem>
								<NavLink className="nav-link" to="/home">
									<span className="fa fa-home fa-lg" /> Home
								</NavLink>
							</NavItem>
							<NavItem>
								<NavLink className="nav-link" to="/aboutus">
									<span className="fa fa-info fa-lg" /> About Us
								</NavLink>
							</NavItem>
							<NavItem>
								<NavLink className="nav-link" to="/menu">
									<span className="fa fa-list fa-lg" /> Menu
								</NavLink>
							</NavItem>
							<NavItem>
								<NavLink className="nav-link" to="/contactus">
									<span className="fa fa-address-card fa-lg" /> Contact Us
								</NavLink>
							</NavItem>
						</Nav>
						<Nav className="ml-auto" navbar>
							<NavItem>
								<Button outline onClick={this.toggleModal}>
									<span className="fa fa-sign-in"/>Login
								</Button>
							</NavItem>
						</Nav>
						</Collapse>
					</div>
				</Navbar>
				<Jumbotron>
					<div className="container">
						<div className="row row-header">
							<div className="col-12 col-sm-6">
								<h1>Ristorante Con Fusion</h1>
								<p>We take inspiration from the World's best cuisines, andcreate a unique fusion experience. Our lipsmacking creations willtickle your culinary senses!</p>
							</div>
						</div>
					</div>
				</Jumbotron>
				<Modal isOpen={this.state.isModalOpen} toggle={this.toggleModal}>
					<ModalHeader toggle={this.toggleModal}>
						Login
					</ModalHeader>
					<ModalBody>
						<Form onSubmit={this.handleLogin}>
							<FormGroup>
								<Label htmlfor="username">Username</Label>
								<Input type="text" name="username" id="username"
									innerRef={(input) => this.username = input}/>
							</FormGroup>
							<FormGroup>
								<Label htmlfor="password">Password</Label>
								<Input type="password" name="password" id="password"
								innerRef={(input) => this.password = input}/>
							</FormGroup>
							<FormGroup check>
								<Label check>
									<Input type="checkbox" name="remember" id="remember"
									innerRef={(input) => this.remember = input}/>
									Remember me
								</Label>
							</FormGroup>
							<Button type="submit" value="submit" className="bg-primary">Login</Button>
						</Form>
					</ModalBody>
				</Modal>
			</>
		);
	}
}

export default Header;