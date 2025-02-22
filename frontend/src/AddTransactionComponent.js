import axios from "axios";
import {useState} from "react";

export default function AddTransactionComponent(){
    const [amount, setAmount] = useState(null);
    const [date, setDate] = useState(null);
    const [type, setType] = useState(null);
    const [operationMessage, setOperationMessage] = useState(null);

    return (
        <div style={{
            border: '1px solid black',
            borderRadius: 10,
            paddingLeft: 10,
            paddingRight: 10,
            paddingBottom: operationMessage == null ? 5 : 0
        }}>
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
                               checked={type === "enter" ? true : false}/>
                        Enter
                    </label>
                    <label>
                        <input type={"radio"} onChange={() => setType("exit")}
                               checked={type === "exit" ? true : false}/>
                        Exit
                    </label>
                </div>
                <div>
                    <button style={{marginTop: 45}} onClick={() => {
                        if (amount == null || amount <= 0) {
                            setOperationMessage("Please enter amount");
                            return;
                        }
                        if (isNaN(new Date(date)) || date == null || new Date(date) > new Date()) {
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
        if(response.status === 200)
            return "Transaction added successfully"
        return response.data;
    }
    catch (error) {
        return "An unknown error occurred. Check the log to see more details."
    }
}