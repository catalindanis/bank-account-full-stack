import './App.css';
import {useState} from "react";
import axios from "axios";

function App() {

    const [amount, setAmount] = useState(null);
    const [date, setDate] = useState(null);
    const [type, setType] = useState(null);
    const [firstRadio, setFirstRadio] = useState(null);
    const [operationMessage, setOperationMessage] = useState(null);
    //const axios = require('axios');

    return (
        <div className="App" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                <img src="./logo.png" style={{height: 25, marginRight: 10}}/>
                <h1>Bank account</h1>
            </div>
            <div style={{border: '1px solid black', borderRadius: 10, paddingLeft: 10, paddingRight: 10, paddingBottom: operationMessage == null ? 5 : 0}}>
                <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center', marginTop: -10}}>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Amount</h4>
                        <input type={"number"} onChange={e => setAmount(e.target.value)} placeholder="Enter amount:"/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Date</h4>
                        <input type={"date"} onChange={e => setDate(e.target.value)}/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Type</h4>
                        <label style={{marginRight: 10}}>
                            <input type={"radio"} onChange={() => setType("enter")}
                                   checked={type == "enter" ? true : false}/>
                            Enter
                        </label>
                        <label>
                            <input type={"radio"} onChange={() => setType("exit")}
                                   checked={type == "exit" ? true : false}/>
                            Exit
                        </label>
                    </div>
                    <div>
                        <button style={{marginTop: 45}} onClick={() => {
                            if (amount == null || amount <= 0) {
                                setOperationMessage("Please enter amount");
                                return;
                            }
                            if (isNaN(new Date(date)) || date == null) {
                                setOperationMessage("Please enter a valid date");
                                return;
                            }
                            if (type == null) {
                                setOperationMessage("Please select type");
                                return;
                            }

                            const result = addTransaction(amount, date, type);
                            setOperationMessage(result);
                        }}>
                            Add transaction
                        </button>
                    </div>
                </div>
                <p style={{marginTop: 5, marginBottom: 5}}>{operationMessage}</p>
            </div>
        </div>
    );
}

async function addTransaction(amount, date, type){
    try{
        const response = await axios.post('http://localhost:8080/add', null,
            {
                params: {
                    date: date.toString(),
                    amount: amount,
                    type: type.toString(),
                }
            })
        if(response.status == 200)
            return "Transaction added successfully"
        return response.data;
    }
    catch (error) {
        return "An unknown error occurred. Check the log to see more details."
    }
}

export default App;
