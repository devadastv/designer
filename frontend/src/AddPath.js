import React, { Component } from 'react';

class AddPath extends Component {

    state = {
        path: '',
        description: 'dummy description'
    }

    handleChange = (e) => {
        this.setState({
            path: e.target.value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.addPath(this.state);
        this.setState({
            path: ''
        })
    }

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <label>Add new path:</label>
                    <input type="text" onChange={this.handleChange} value={this.state.path}></input>
                </form>
            </div>
        )
    }
}

export default AddPath;