import axios from "axios";
import {useState} from "react";
import {updateTransactions} from "./TransactionsHistoryComponent";

export default function AddTransactionComponent({transactions, setTransactions}){
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
                               checked={type === "enter"}/>
                        Enter
                    </label>
                    <label>
                        <input type={"radio"} onChange={() => setType("exit")}
                               checked={type === "exit"}/>
                        Exit
                    </label>
                </div>
                <div>
                    <button style={{marginTop: 45}} onClick={() => {
                        const validateResult = validateTransaction(amount, date, type);
                        if(validateResult !== true) {
                            setOperationMessage(validateResult);
                            return;
                        }

                        const result = addTransaction(amount, date, type, transactions, setTransactions);
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

export function validateTransaction(amount, date, type){
    if (amount == null || amount <= 0) {
        return "Please enter amount";
    }
    if (isNaN(new Date(date)) || date == null || new Date(date) > new Date()) {
        return "Please enter a valid date"
    }
    if (type == null) {
        return "Please select type";
    }
    return true;
}

async function addTransaction(amount, date, type, transactions, setTransactions){
    try{
        const response = await axios.post('http://localhost:8080/add', null,
            {
                params: {
                    date: date.toString(),
                    amount: amount,
                    type: type.toString(),
                }
            })
        if(response.status === 200) {
            updateTransactions(transactions, setTransactions);
            return "Transaction added successfully"
        }
        return response.data;
    }
    catch (error) {
        return "An unknown error occurred. Check the log to see more details."
    }
}