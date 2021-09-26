import React, { Component } from 'react'
import axios from 'axios'
import Paths from './Paths'
import AddPath from './AddPath'
class App extends Component {

  state = {
    paths: []
  }

  componentDidMount() {
    axios
      .get("/backend/paths")
      .then(res => {
        console.log(res)
        this.setState({
          paths: res.data
        })
      })
  }

  refreshPathList = () => {
    axios
      .get("/backend/paths")
      .then(res => {
        console.log(res)
        this.setState({
          paths: res.data
        })
      })
  }

  createPathInBackend = (path) => {
    console.warn(">>>Just before axios...");
    console.warn(path);
    axios
    .post("/backend/paths", path, {
      headers: {
        'content-type': 'application/json'
      }
    })
    .then(res => {
      console.log(res)
      // this.setState({
      //   paths: res.data
      // })
    })
  }

  addPath = (path) => {
    let paths = [...this.state.paths, path];
    this.setState({
      paths: paths
    });
    console.warn(">>>About to create in backend...");
    console.warn(path);
    this.createPathInBackend(path);
  }

  render() {
    return (
      <div className="paths-app container">
        <header className="App-header">
          <h1 className="center blue-text">Paths</h1>
          <Paths paths={this.state.paths} />
          <br />
          <AddPath addPath={this.addPath} />
          <button class="waves-effect waves-light btn" onClick={this.refreshPathList}>Refresh</button>
        </header>
      </div>
    );
  }
}

export default App;
