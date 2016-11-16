import React, {Component} from "react";
import NavBar from "../navbar";
import client from '../../services/client';

class MainLayout extends Component {
    constructor(props) {
        super(props);
        this.state = {
            url: '/api/me',
            profile: {}
        }
    }

    componentWillMount() {
        client({method: 'GET', path: '/api/me'}).then(response => {
            if (response.status.code == 200) {
                this.setState({profile: response.entity});
            }
        });
       /* var self = this;
        $.ajax({
            url: this.state.url,
            dataType: 'json',
            async: false,
            success: function (response) {
                self.setState({profile: response});
            }
        });*/

    }

    render() {
        return (
            <div className="container-fluid">
                <NavBar brand="ZooPlus Challenge" profile={this.state.profile}/>
                {this.props.children}
            </div>
        )
    }
}

export default MainLayout;
