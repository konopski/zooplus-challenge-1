import React, {Component} from "react";
import {Link} from "react-router";

class NavBar extends Component {

    constructor(props) {
        super(props);
        this.state = {
        }
    }

    componentWillMount() {
    }

    render() {
        return (
            <nav className="navbar navbar-default navbar-fixed-top">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#navbar"
                                aria-expanded="false" aria-controls="navbar">
                            <span className="sr-only">Toggle navigation</span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <a className="navbar-brand" href="#">{this.props.brand}</a>
                    </div>
                    <div classID="navbar" className="navbar-collapse collapse">
                        <div className="navbar-form navbar-right">
                            <span>Hello {this.props.profile.email} </span>
                            &nbsp;&nbsp;&nbsp;
                            <a href="/logout" className="btn btn-default">
                                Sign Out
                            </a>
                        </div>

                    </div>
                </div>
            </nav>
        )
    }
}

export default NavBar;