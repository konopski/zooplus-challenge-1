import React, {Component} from "react";
import CurrencyCalculator from "./currency-calculator";
import client from '../services/client';

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currencies: [],
            isLoading: true
        }
    }

    componentWillMount() {
        client({method: 'GET', path: this.props.apiUrl.exchanges + '/currencies'}).then(response => {
            if (response.status.code == 200) {
                this.setState({currencies: response.entity, isLoading: false});
            }
        });
    }

    render() {
        if (this.state.isLoading) {
            return (<div>Loading ...</div>)
        } else {
            return (<CurrencyCalculator currencies={this.state.currencies} />)
        }
    }
}

export default Home;
Home.defaultProps = {apiUrl: {exchanges: '/api/exchanges'}};