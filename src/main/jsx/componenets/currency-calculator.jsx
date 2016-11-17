import React, {Component} from "react";
import client from "../services/client";

class CurrencyCalculator extends Component {

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.convert = this.convert.bind(this);
        this.getHistorical = this.getHistorical.bind(this);
        this.fillForm = this.fillForm.bind(this);
        this.state = {
            response: false,
            form: {
                currency: this.props.currencies[0],
                amount: 1,
                date: moment().format('YYYY-MM-DD')
            },
            view: {},
            historical: []
        }
    }


    componentWillMount() {
        this.getHistorical();
    }

    handleChange(e) {
        this.state.form[e.target.name] = e.target.value;
        this.setState({form: this.state.form});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.convert();
    }

    getHistorical() {
        client({method: 'GET', path: this.props.apiUrl.exchanges})
            .then(response => {
                if (response.status.code == 200 && response.entity._embedded) {
                    this.setState({historical: response.entity._embedded.exchanges});
                }
            });
    }

    convert() {
        client({
            method: 'GET',
            path: this.props.apiUrl.exchanges + '/convert/' + this.state.form.currency + '/' + this.state.form.amount + '/' + this.state.form.date
        }).then(response => {
            if (response.status.code == 200) {
                if (this.state.historical.unshift(response.entity) > 10) {
                    this.state.historical.pop();
                }
                this.setState({view: response.entity, response: true});
            }
        });
    }

    fillForm(form) {
        this.setState({view: form, response: true});
    }

    renderForm() {
        const options = this.props.currencies.map(option => <option key={option} value={option}>{option}</option>);
        return (
            <form className="form-horizontal" onSubmit={this.handleSubmit}>
                <div className="form-group">
                    <label htmlFor="currency" className="col-sm-2 control-label">Currency</label>
                    <div className="col-sm-10">
                        <select classID="currency" name="currency" className="form-control"
                                onChange={this.handleChange} value={this.state.form.currency}>
                            {options}
                        </select>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="amount" className="col-sm-2 control-label">Amount</label>
                    <div className="col-sm-10">
                        <input classID="amount" name="amount" type="number" className="form-control"
                               required="required" min="1" max="99999999999999"
                               onChange={this.handleChange} value={this.state.form.amount}/>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="date" className="col-sm-2 control-label">Date</label>
                    <div className="col-sm-10">
                        <input classID="date" name="date" type="date" className="form-control"
                               required="required"
                               onChange={this.handleChange} value={this.state.form.date}/>
                    </div>
                </div>
                <div className="form-group">
                    <div className="col-sm-offset-2 col-sm-10">
                        <button type="submit" className="btn btn-primary">Calculate</button>
                    </div>
                </div>
            </form>
        )
    }

    renderResult() {
        if (this.state.response) {
            var rates = [];
            for (var fieldName in this.state.view.result) {
                rates.push(<div key={fieldName}>{fieldName} - {this.state.view.result[fieldName]}</div>)
            }
            return (
                <div>
                    <h4>The conversion of {this.state.view.amount} {this.state.view.currency} which was
                        made
                        on {this.state.view.date} is equal with :</h4>
                    {rates}
                </div>
            );
        } else {
            return (<div></div>)
        }
    }

    render() {
        return (
            <div className="container-fluid">
                <div>
                    <div className="col-md-12">
                        <h2 className="title text-center">Currency Calculator</h2>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6">
                        {this.renderForm()}
                    </div>
                    <div className="col-md-6">
                        {this.renderResult()}
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <h2 className="title text-center">Historical Results</h2>
                        <table className="table table-hover">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Date</th>
                                <th>Currency</th>
                                <th>Amount</th>
                                <th>Show Result</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.state.historical.map(h =>
                                <tr key={h.createdDate}>
                                    <th>{moment(h.createdDate).format('YYYY-MM-DD HH:MM:SS')}</th>
                                    <th>{h.date}</th>
                                    <th>{h.currency}</th>
                                    <th>{h.amount}</th>
                                    <th>
                                        <button type="button" className="btn btn-primary" onClick={this.fillForm.bind(this,h)}>Show</button>
                                    </th>
                                </tr>
                            )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

export default CurrencyCalculator;
CurrencyCalculator.defaultProps = {apiUrl: {exchanges: '/api/exchanges'}};