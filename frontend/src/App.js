import './App.css';
import {useState} from "react";
import AddTransactionComponent from "./AddTransactionComponent";

function App() {

    return (
        <div className="App" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                <img src="./logo.png" style={{height: 25, marginRight: 10}}/>
                <h1>Bank account</h1>
            </div>
            <AddTransactionComponent></AddTransactionComponent>
            <div style={{border: '1px solid black', marginTop: 30, width: '585px', borderRadius: 10}}>
                <h3 style={{marginTop: 5, marginBottom: 5, textAlign: "left", marginLeft: 10}}>Transactions history</h3>
            </div>
        </div>
    );
}



export default App;
