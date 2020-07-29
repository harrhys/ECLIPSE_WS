import React from 'react';
import { Card, CardImg, CardText, CardBody, Breadcrumb, 
	BreadcrumbItem, CardTitle,Modal, ModalHeader, ModalBody,
	Button,  Label,  Col, Row} from 'reactstrap';
import {Control, LocalForm, Errors} from 'react-redux-form';
import {Link} from 'react-router-dom';
import {Loading} from './LoadingComponent';
import {baseUrl} from '../shared/baseUrl';

    function RenderDish({dish}) {
       
        return(
			<div  className="col-12 col-md-5 m-1">
                <Card>
                    <CardImg top src={baseUrl+dish.image} alt={dish.name} />
                    <CardBody>
	                  	<CardTitle>{dish.name}</CardTitle>
	                  	<CardText>{dish.description}</CardText>
                    </CardBody>
                </Card>
			</div>
        );
       
    }

	function RenderComments({comments,postComment,dishId, errMess}) {

		if(errMess!=null){

			return(
				<div>{errMess}</div>
			);

		}

		else{
		
			const dishcomments = comments.map(
				(dishcomment) => {
					return(	
							<li key={dishcomment.id}>
							<blockquote  className="blockquote">
								<p className="mb-0">{dishcomment.comment}</p>
								<p className="mb-0">
								-- {dishcomment.author}, { 
									new Intl.DateTimeFormat('en-US', {year:'numeric', month:'short', day:'2-digit'})
									.format(new Date(Date.parse(dishcomment.date)))
									}
								</p>
							</blockquote>
							</li>
						
					);
				}
			);

			return(
				<div  className="col-12 col-md-5 m-1">
					<h2>Comments</h2>
						<ul className="list-unstyled">
							{dishcomments}
						</ul>
						<CommentsForm dishId={dishId} postComment={postComment}/>
				</div>
			);
		}
    }
	
	const DishDetail = (props) => {
	
		if(props.isLoading)
		{
			return(
				<div className="container">
					<div className="row">
						<Loading />
					</div>
				</div>

			);
		}

		else if(props.errMess!=null)
		{
			return(
				<div className="container">
					<div className="row">
						<h4>{props.disherrMess}</h4>
					</div>
				</div>

			);
		}
			
			
		else
				
			return(
				<div className="container">
					<div className="row">
	                    <Breadcrumb>
 							<BreadcrumbItem><Link to="/menu">Menu</Link></BreadcrumbItem>
	                        <BreadcrumbItem active>{props.selectedDish.name}</BreadcrumbItem>
	                    </Breadcrumb>
	                    <div className="col-12">
	                        <h3>{props.selectedDish.name}</h3>
	                        <hr />
	                    </div>                
               		</div>
		        	<div className="row">
		                	<RenderDish dish={props.selectedDish} />
							<RenderComments 
								comments={props.comments} 
								postComment={props.postComment}
								dishId={props.selectedDish.id}
								errMess={props.comments.commentsErrMess}
								/>
		        	</div>
				</div> 
			);
	}

	const required = (val) => val && val.length;
	const maxLength = (len) => (val) => !(val) || (val.length <= len);
	const minLength = (len) => (val) => val && (val.length >= len);

	class CommentsForm extends React.Component{

		constructor(props){

			super(props);
			this.state = {
				isModalOpen:false
			};
			this.toggleModal = this.toggleModal.bind(this);
			this.handleSubmit = this.handleSubmit.bind(this);

		}

		toggleModal(){

			this.setState(
				{
					isModalOpen:!this.state.isModalOpen
				}		
			);
		}

		handleSubmit(values) {
			this.toggleModal();
			this.props.postComment(this.props.dishId, values.rating, values.author, values.message);
		}

		render(){

			return(
				<>
				<Button outline onClick={this.toggleModal}>
					<span className="fa fa-pencil"/> Submit Comment
				</Button>
				<Modal isOpen={this.state.isModalOpen} toggle={this.toggleModal}>
					<ModalHeader toggle={this.toggleModal}>
						Submit Comment
					</ModalHeader>
					<ModalBody>
						<LocalForm onSubmit={values => this.handleSubmit(values)}>
							<Row className="form-group">
								<Label htmlFor="rating" md={2}>Rating</Label>
								<Col md={10}>
								<Control.select model=".rating" name="rating"
									className="form-control">
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
									</Control.select>
								</Col>
							</Row>
							<Row className="form-group">
								<Label htmlFor="author" md={2}>Your Name</Label>
								<Col md={10}>
									<Control.text model=".author" id="author" name="author"
									placeholder="Your Name"
									className ="form-control"
									validators={{
										required, minLength: minLength(3), maxLength: maxLength(15)
									}}
									/>
									<Errors
                                        className="text-danger"
                                        model=".author"
                                        show="touched"
                                        messages={{
                                            required: 'Required',
                                            minLength: 'Must be greater than 2 characters',
                                            maxLength: 'Must be 15 characters or less'
                                        }}
                                     />
								</Col>
							</Row>
							
							<Row className="form-group">
								<Label htmlFor="message" md={2}>Your Feedback</Label>
								<Col md={10}>
									<Control.textarea model=".message" id="message" name="message"
									rows="12" className="form-control"
									validators={{
										required, minLength: minLength(3), maxLength: maxLength(15)
									}}
									></Control.textarea>
									<Errors
                                        className="text-danger"
                                        model=".message"
                                        show="touched"
                                        messages={{
                                            required: 'Required',
                                            minLength: 'Must be greater than 2 characters',
                                            maxLength: 'Must be 15 characters or less'
                                        }}
                                     />
								</Col>
							</Row>
							<Row className="form-group">
								<Col md={{size: 10, offset: 2}}>
									<Button type="submit" color="primary">
										Send Feedback
									</Button>
								</Col>
							</Row>
						</LocalForm>
						
					</ModalBody>
				</Modal>
				</>
			)
		}
	}

export default DishDetail;